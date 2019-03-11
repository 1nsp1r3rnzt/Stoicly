package codehealthy.com.stoicly.data;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseCopier {
    public static DatabaseCopier get;

    private DatabaseCopier() {
    }

    static synchronized DatabaseCopier getInstance() {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new DatabaseCopier();
        }
        return Holder.INSTANCE;
    }

    void copyIfNoDataBaseFound(Context context, String dbName) {
        final File dbPath = context.getDatabasePath(dbName);
        if (!dbPath.exists()) {
            dbPath.getParentFile().mkdirs();
            try {
                final InputStream fileInputStream = context.getAssets().open("databases/" + dbName);
                final OutputStream outputStream = new FileOutputStream(dbPath);
                byte[] buffer = new byte[8192];
                int length;
                while ((length = fileInputStream.read(buffer, 0, 8192)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                fileInputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Holder {
        static DatabaseCopier INSTANCE;
    }
}
