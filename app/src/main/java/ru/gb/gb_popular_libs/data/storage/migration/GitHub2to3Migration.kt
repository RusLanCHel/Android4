package ru.gb.gb_popular_libs.data.storage.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object GitHub2to3Migration : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE github_users ADD COLUMN name TEXT DEFAULT NULL")
    }

}