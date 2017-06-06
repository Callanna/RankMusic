package com.callanna.rankmusic.ui.customview

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import java.util.*

/**
 * Created by Callanna on 2017/6/6.
 */

class LineScaleIndicator : Indicator() {

    internal var scaleYFloats = floatArrayOf(SCALE, SCALE, SCALE, SCALE, SCALE)

    override fun draw(canvas: Canvas, paint: Paint) {
        Log.d("duanyl","draw")
        paint.isAntiAlias = true
        val translateX = (width / 11).toFloat()
        val translateY = (height / 2).toFloat()
        for (i in 0..4) {
            canvas.save()
            canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY)
            canvas.scale(SCALE, scaleYFloats[i])
            val rectF = RectF(-translateX / 2, -height / 2.5f, translateX / 2, 2.0f)
            canvas.drawRoundRect(rectF, 2f, 2f, paint)
            canvas.restore()
        }
    }

    override fun onCreateAnimators(): ArrayList<ValueAnimator> {
        val animators = ArrayList<ValueAnimator>()
        //        long[] delays=new long[]{100,200,300,400,500};
        val delays = longArrayOf(400, 200, 0, 200, 400)
        for (i in 0..4) {
            val index = i
            val scaleAnim = ValueAnimator.ofFloat(1.0f, 0.4f, 1.0f)
            scaleAnim.duration = 1000
            scaleAnim.repeatCount = -1
            scaleAnim.startDelay = delays[i]
            addUpdateListener(scaleAnim, ValueAnimator.AnimatorUpdateListener { animation ->
                scaleYFloats[index] = animation.animatedValue as Float
               // invalidateSelf()
                postInvalidate()
            })
            animators.add(scaleAnim)
        }
        return animators
    }

    companion object {

        val SCALE = 1.0f
    }
}
