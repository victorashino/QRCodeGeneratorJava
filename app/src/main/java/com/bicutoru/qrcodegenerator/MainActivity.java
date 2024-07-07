package com.bicutoru.qrcodegenerator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity {

    EditText editLink;
    Button buttonGenerate;
    ImageView imgQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponents();

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editLink.getText().toString())) {
                    editLink.setError("Link is required");
                    editLink.requestFocus();
                } else {
                    gernerateQRCode(editLink.getText().toString());
                }
            }
        });
    }

    private void initComponents() {
        editLink = findViewById(R.id.editLink);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        imgQRCode = findViewById(R.id.imgQRCode);
    }

    private void gernerateQRCode(String link) {
        QRCodeWriter qrCode = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = qrCode.encode(link, BarcodeFormat.QR_CODE, 196, 196);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            Bitmap bitMap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitMap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            imgQRCode.setImageBitmap(bitMap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}