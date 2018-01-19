package br.ufrr.serversoket;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements DataDisplay{
    EditText ip_ad, msg;
    TextView server_msg;
    Button btn_startServer;
    String ip_address;
    String serverMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip_ad = findViewById(R.id.ip_add);
        msg = findViewById(R.id.msg_server);
        server_msg = findViewById(R.id.server_msg);
        btn_startServer = findViewById(R.id.btn_start);

        btn_startServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip_address = ip_ad.getText().toString();
                serverMessage = msg.getText().toString();

                if(!ip_address.isEmpty() && !serverMessage.isEmpty() && ip_address.contains(".")){
                    Toast.makeText(MainActivity.this, R.string.sending_msg, Toast.LENGTH_SHORT).show();
                    connect(view,serverMessage);
                }
                else{
                    Toast.makeText(MainActivity.this, R.string.msg_failed, Toast.LENGTH_SHORT).show();
                    if(ip_address.isEmpty() || !ip_address.contains("."))
                        ip_ad.requestFocus();
                    else if(serverMessage.isEmpty())
                        server_msg.requestFocus();
                }

            }
        });
    }
    public void connect(View view, String msg_server){
        MyServer server = new MyServer();
        server.setEventListener(this);
        server.startListening(msg_server);
    }
    public void Display(String message){
        server_msg.setText(""+message);
    }
}
