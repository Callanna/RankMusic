package com.ldm.kotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

/**
 * description：
 * 作者：ldm
 * 时间：20162016/8/6 16:29
 * 邮箱：1786911211@qq.com
 */
class DBHelper(ctx: Context = App.instance()) : ManagedSQLiteOpenHelper(ctx,
        DB_NAME, null, DB_VERSION) {
    //数据库相关字段
    companion object {
        val DB_NAME = "forecast.db"//数据库名
        val DB_VERSION = 1//版本
        val instance by lazy { DBHelper() }
    }

    //定义学生表字段
    object StudentTable {
        val T_NAME = "student"//数据表名称
        val ID = "_id"       //id
        val NAME = "name"    //学生名称
        val SEX = "sex"      //学生性别
        val AGE = "age"      //学生年龄
        val SCORE = "score"  //学生成绩
        val TEACHER_ID = "teacherId" //对应教师id
    }

    //定义教师表字段
    object TeacherTable {
        val T_NAME = "teacher"    //教师对应地表名
        val ID = "_id"           //id
        val NAME = "name"        //老师姓名
        val SUBJCET = "subject"  //所教科目
        val LEVEL = "level"      //老师教研水平
    }

    override fun onCreate(db: SQLiteDatabase) {
        //创建学生对应地表
        db.createTable(StudentTable.T_NAME, true,
                StudentTable.ID to INTEGER + PRIMARY_KEY,
                StudentTable.NAME to TEXT,
                StudentTable.SEX to TEXT,
                StudentTable.AGE to INTEGER,
                StudentTable.SCORE to TEXT,
                StudentTable.TEACHER_ID to TEXT)
        //创建老师对应的表
        db.createTable(TeacherTable.NAME, true,
                TeacherTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeacherTable.NAME to TEXT,
                TeacherTable.SUBJCET to TEXT,
                TeacherTable.LEVEL to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //数据库升级时，先删除表再创建
        db.dropTable(StudentTable.T_NAME, true)
        db.dropTable(TeacherTable.T_NAME, true)
        onCreate(db)
    }

}
