package fr.eps.handballcontrats;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@CapacitorPlugin(name = "DocumentSaver")
public class DocumentSaverPlugin extends Plugin {

    @PluginMethod
    public void saveToDocuments(PluginCall call) {
        String base64Data = call.getString("data");
        String filename = call.getString("filename");
        String mimeType = call.getString("mimeType", "application/pdf");

        if (base64Data == null || filename == null) {
            call.reject("Paramètre manquant : 'data' ou 'filename'");
            return;
        }

        try {
            byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
            Context context = getContext();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
                values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

                Uri collection = MediaStore.Files.getContentUri("external");
                Uri itemUri = context.getContentResolver().insert(collection, values);

                if (itemUri == null) {
                    call.reject("Impossible de créer le fichier dans Documents");
                    return;
                }

                OutputStream out = context.getContentResolver().openOutputStream(itemUri);
                if (out == null) {
                    call.reject("Impossible d'ouvrir le fichier pour écriture");
                    return;
                }
                out.write(bytes);
                out.flush();
                out.close();

                JSObject ret = new JSObject();
                ret.put("uri", itemUri.toString());
                call.resolve(ret);

            } else {
                File documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                if (!documentsDir.exists()) {
                    documentsDir.mkdirs();
                }
                File file = new File(documentsDir, filename);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.flush();
                fos.close();

                JSObject ret = new JSObject();
                ret.put("uri", file.getAbsolutePath());
                call.resolve(ret);
            }
        } catch (Exception e) {
            call.reject("Erreur lors de l'enregistrement : " + e.getMessage(), e);
        }
    }
}
