package ar.com.sistema.perritosfinderapp;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HiloConexion extends Thread{
    Handler handler;
    String urlImg;

    public HiloConexion(Handler handler) {
        this.handler = handler;
    }

    public HiloConexion(Handler handler, String urlImg) {
        this.handler = handler;
        this.urlImg = urlImg;
    }

    @Override
    public void run() {
        ConexionHTTP conexionHTTP = new ConexionHTTP();

            byte[] img = conexionHTTP.obtenerRespuesta(this.urlImg);
            Message msg = new Message();
            msg.obj = img;
            handler.sendMessage(msg);

    }

}
