package com.oblivion.qqxmpp;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.test.ApplicationTestCase;

import com.oblivion.qqxmpp.provider.ContactProvider;
import com.oblivion.qqxmpp.utils.Change2PinYinUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testInsert() {
        ContentValues values = new ContentValues();
        values.put(ContactProvider.CONTACT_FIELD.ACCOUNT, "哈哈");
        values.put(ContactProvider.CONTACT_FIELD.AVATAR, "1");
        values.put(ContactProvider.CONTACT_FIELD.NICK, "haha");
        values.put(ContactProvider.CONTACT_FIELD.SORT, Change2PinYinUtils.getPinYin("刘杰"));
        getContext().getContentResolver().insert(ContactProvider.URI, values);
    }

    public void testUpdate() {
        ContentValues values = new ContentValues();
        values.put(ContactProvider.CONTACT_FIELD.NICK, "jack");
        getContext().getContentResolver().update
                (ContactProvider.URI, values, ContactProvider.CONTACT_FIELD.NICK + "=?", new String[]{"oblivion"});
    }

    public void testDelete() {
        getContext().getContentResolver().delete(ContactProvider.URI, ContactProvider.CONTACT_FIELD.ACCOUNT + "=?", new String[]{"刘杰"});
    }

    public void testFind() {
        Cursor cursor = getContext().getContentResolver().query(ContactProvider.URI, null, null, null, null);
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String string = cursor.getString(i);
                System.out.println(string);
            }

        }
        cursor.close();
    }
}