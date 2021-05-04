package com.example.pronosticapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DbUnitTest {
    private PronosticDbHelper dbHelper;
    private Context appContext;
    private PronosticDbContext ctx;

    @Before
    public void setup() {
        appContext = ApplicationProvider.getApplicationContext();
        dbHelper = new PronosticDbHelper(appContext);
        ctx = new PronosticDbContext(appContext);
    }

    @After
    public void clean() {
        appContext.deleteDatabase(dbHelper.getDatabaseName());
    }

    @Test
    public void createUserDbTest(){
        long rowId = ctx.insertUser(new User("jebbarirayhane@gmail.com", "JEBBARI", "Rayhane", Role.Admin));
        Assert.assertNotNull(rowId);
    }

    @Test
    public void getUserDbTest(){
        long rowId = ctx.insertUser(new User("jebbarirayhane@gmail.com", "JEBBARI", "Rayhane", Role.Admin));
        User user = ctx.getUser("jebbarirayhane@gmail.com");
        Assert.assertNotNull(user);
    }

    @Test
    public void deleteUserDbTest(){
        long rowId = ctx.insertUser(new User("jebbarirayhane@gmail.com", "JEBBARI", "Rayhane", Role.Admin));
        int deletedRows = ctx.deleteUser(rowId);
        Assert.assertEquals(1, deletedRows);
    }

    @Test
    public void updateUserDbTest(){
        long rowId = ctx.insertUser(new User("jebbarirayhane@gmail.com", "JEBBARI", "Rayhane", Role.Admin));
        int count = ctx.updateUser(new User(rowId, "jebbarirayhane@gmail.com", "JEANLOUIS", "Rayhane", Role.Admin));
        Assert.assertEquals(1, count);
    }
}
