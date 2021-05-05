package com.example.pronosticapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.speech.RecognitionListener;

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
        long rowId = ctx.insertUser(new User("jebbarirayhane@gmail.com", "fake", "JEBBARI", "Rayhane", Role.Admin));
        Assert.assertNotNull(rowId);
    }

    @Test
    public void getUserDbTest(){
        long rowId = ctx.insertUser(new User("jebbarirayhane@gmail.com", "fake","JEBBARI", "Rayhane", Role.Admin));
        User user = ctx.getUser("jebbarirayhane@gmail.com");
        Assert.assertNotNull(user);
    }

    @Test
    public void deleteUserDbTest(){
        long rowId = ctx.insertUser(new User("jebbarirayhane@gmail.com", "fake","JEBBARI", "Rayhane", Role.Admin));
        int deletedRows = ctx.deleteUser(rowId);
        Assert.assertEquals(1, deletedRows);
    }

    @Test
    public void updateUserDbTest(){
        long rowId = ctx.insertUser(new User("jebbarirayhane@gmail.com", "fake",  "JEBBARI", "Rayhane", Role.Admin));
        int count = ctx.updateUser(new User(rowId, "jebbarirayhane@gmail.com", "fake", "JEANLOUIS", "Rayhane", Role.Admin));
        Assert.assertEquals(1, count);
    }

    @Test
    public void createRencontreDbTest(){
        long rowId = ctx.insertRencontre(new Rencontre("match", "20/10/2004", "Ligue 1", "PSG", "LYON", "PSG"));
        Assert.assertNotNull(rowId);
    }

    @Test
    public void getAllRecontreDbTest(){
        ctx.insertRencontre(new Rencontre("match1", "20/10/2004", "Ligue 1", "PSG", "LYON", "PSG"));
        ctx.insertRencontre(new Rencontre("match2", "20/10/2004", "Ligue 1", "PSG", "MARSEILLE", "PSG"));
        List<Rencontre> rencontres = ctx.getAllRecontre();
        Assert.assertEquals(2, rencontres.size());
    }

    @Test
    public void deleteRecontreDbTest(){
        Rencontre rencontre = new Rencontre("match1", "20/10/2004", "Ligue 1", "PSG", "LYON", "PSG");
        Long rowId = ctx.insertRencontre(rencontre);
        int deletedRows = ctx.deleteRencontre(rowId);
        Assert.assertEquals(1, deletedRows);
    }

    @Test
    public void updateRecontreDbTest(){
        long rowId = ctx.insertRencontre(new Rencontre("match", "20/10/2004", "Ligue 1", "PSG", "LYON", "PSG"));
        int count = ctx.updateRencontre(new Rencontre(rowId,"match3", "20/10/2004", "Ligue 1", "BASTIA", "LYON", "PSG"));
        Assert.assertEquals(1, count);
    }
}
