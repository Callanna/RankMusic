# RankMusic 1.2 
  继续上一个版本，主要做了屏幕适配  
* 1.屏幕尺寸适配，密度适配。  
* 2.状态栏透明  
* 3.播放界面切入，退出动画   

**让小伙伴们看看屏幕适配效果。横屏，竖屏随意切换.**  

![image](https://raw.githubusercontent.com/Callanna/RankMusic/master/art/demo1.gif)        ![image](https://raw.githubusercontent.com/Callanna/RankMusic/master/art/demo2.gif)

## 屏幕尺寸适配
   现在在大尺寸HD（10.1寸,9寸，7寸）  
   手机或小尺寸设备上（4.3寸，5寸，4.7寸等）都可以很好的适配。    
   想要看到两种效果，手机上横屏，竖屏切换即可。  
   
   横屏，竖屏切换采用了不同的布局，如图目录来区分，横屏，竖屏切换时会相应的加载  
![image](https://raw.githubusercontent.com/Callanna/RankMusic/master/art/res.png)  


## 屏幕密度适配
  
使用洪洋(hongyang)大神的自动屏幕密度适配的框架   
**[AndroidAutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout)**  

```Java

compile 'com.zhy:autolayout:1.4.5'

```
博客介绍：http://blog.csdn.net/lmj623565791/article/details/49990941


## 状态栏透明 

在基类中封装了这个方法 **setupStatuBar(activity: Activity)**  

```Java
  @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //在setContentView() createDataBinding()之前调用
        setupStatuBar(this)
        context = this
        mBinding = createDataBinding(savedInstanceState)
        initView(savedInstanceState)
    }
```
实现过程  

```Java

    @SuppressLint("NewApi")
    protected fun setupStatuBar(activity: Activity) {
        if (Build.VERSION.SDK_INT == 19) {
            val window = activity.window
            val flags = window.attributes.flags
            if (flags or WindowManager.LayoutParams.FLAG_FULLSCREEN != flags) {
                window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                val height = getStatusbarHeight(activity)
                val contentView = window
                        .findViewById(Window.ID_ANDROID_CONTENT)
                contentView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                contentView.setPadding(0, height, 0, 0)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                val contentView = window
                        .findViewById(Window.ID_ANDROID_CONTENT)
                contentView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                contentView.setPadding(0, 0, 0, 0)
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
    }

    protected fun getStatusbarHeight(context: Context): Int {

        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = Integer.parseInt(field.get(obj).toString())
            val y = context.resources.getDimensionPixelSize(x)
            return y
        } catch (e: Exception) {
            e.printStackTrace()
            return (context.resources.displayMetrics.density * 20 + 0.5).toInt()
        }

    }

```
## 播放界面切入，退出动画  

在Android5.0以上支持 场景切换动画
 
**ActivityOptions.makeSceneTransitionAnimation**

```Java
 fun startActivity(context: Context, type: String, position: Int = 0, view: View = ImageView(context)) {
            val intent = Intent(context, PlayActivity::class.java)
            intent.putExtra(TYPE, type)
            intent.putExtra(POSITION, position)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT > 21) {
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context as AppCompatActivity, view, "img").toBundle())
            } else {
                context.startActivity(intent)
            }
        }
```