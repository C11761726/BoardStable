package com.eightmile.boardstable;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import org.winplus.serial.utils.LogUtil;

import com.eightmile.boardstable.SerialPortUtil.OnDataReceiveListener;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";

    private static int time_delay = 100;

    private static final int ttyS0_send = 0x01;
    private static final int ttyS1_send = 0x02;
    private static final int ttyS2_send = 0x03;
    private static final int ttyS3_send = 0x04;
    private static final int ttyS0_receive = 0x05;
    private static final int ttyS1_receive = 0x06;
    private static final int ttyS2_receive = 0x07;
    private static final int ttyS3_receive = 0x08;

    private static final int ttymxc1_send = 0x09;
    private static final int ttymxc2_send = 0x0A;
    private static final int ttymxc3_send = 0x0B;
    private static final int ttymxc4_send = 0x0C;
    private static final int ttymxc1_receive = 0x0D;
    private static final int ttymxc2_receive = 0x0E;
    private static final int ttymxc3_receive = 0x0F;
    private static final int ttymxc4_receive = 0x10;

    private static final int ttysWK0_send = 0x11;
    private static final int ttysWK1_send = 0x12;
    private static final int ttysWK2_send = 0x13;
    private static final int ttysWK3_send = 0x14;
    private static final int ttysWK0_receive = 0x15;
    private static final int ttysWK1_receive = 0x16;
    private static final int ttysWK2_receive = 0x17;
    private static final int ttysWK3_receive = 0x18;

    private static final int PING_RESULT = 0x19;

    private int ttyS0_send_size = 0;
    private int ttyS0_receive_size = 0;
    private int ttyS1_send_size = 0;
    private int ttyS1_receive_size = 0;
    private int ttyS2_send_size = 0;
    private int ttyS2_receive_size = 0;
    private int ttyS3_send_size = 0;
    private int ttyS3_receive_size = 0;

    private int ttymxc1_send_size = 0;
    private int ttymxc1_receive_size = 0;
    private int ttymxc2_send_size = 0;
    private int ttymxc2_receive_size = 0;
    private int ttymxc3_send_size = 0;
    private int ttymxc3_receive_size = 0;
    private int ttymxc4_send_size = 0;
    private int ttymxc4_receive_size = 0;

    private int ttysWK0_send_size = 0;
    private int ttysWK0_receive_size = 0;
    private int ttysWK1_send_size = 0;
    private int ttysWK1_receive_size = 0;
    private int ttysWK2_send_size = 0;
    private int ttysWK2_receive_size = 0;
    private int ttysWK3_send_size = 0;
    private int ttysWK3_receive_size = 0;

    //显示Ping结果
    private TextView tv_ping_result;
    //跑马灯
    private TextView tv_marquee;
    private EditText et_time_delay;
    private Button btn_ttyALL_onff;
    private Button btn_ttyS0_onff, btn_ttyS1_onff, btn_ttyS2_onff, btn_ttyS3_onff;
    private TextView tv_ttyS0_send, tv_ttyS1_send, tv_ttyS2_send, tv_ttyS3_send;
    private TextView tv_ttyS0_receive, tv_ttyS1_receive, tv_ttyS2_receive, tv_ttyS3_receive;
    private Button btn_ttymxc1_onff, btn_ttymxc2_onff, btn_ttymxc3_onff, btn_ttymxc4_onff;
    private TextView tv_ttymxc1_send, tv_ttymxc2_send, tv_ttymxc3_send, tv_ttymxc4_send;
    private TextView tv_ttymxc1_receive, tv_ttymxc2_receive, tv_ttymxc3_receive, tv_ttymxc4_receive;
    private Button btn_ttysWK0_onff, btn_ttysWK1_onff, btn_ttysWK2_onff, btn_ttysWK3_onff;
    private TextView tv_ttysWK0_send, tv_ttysWK1_send, tv_ttysWK2_send, tv_ttysWK3_send;
    private TextView tv_ttysWK0_receive, tv_ttysWK1_receive, tv_ttysWK2_receive, tv_ttysWK3_receive;

    private boolean ttyALL_exit = true;

    private boolean ttyS0_exit = true;
    private boolean ttyS1_exit = true;
    private boolean ttyS2_exit = true;
    private boolean ttyS3_exit = true;

    private boolean ttymxc1_exit = true;
    private boolean ttymxc2_exit = true;
    private boolean ttymxc3_exit = true;
    private boolean ttymxc4_exit = true;

    private boolean ttysWK0_exit = true;
    private boolean ttysWK1_exit = true;
    private boolean ttysWK2_exit = true;
    private boolean ttysWK3_exit = true;

    Uri uri = null;

    private VideoView vv_play;

    private SerialPortUtil spu_one, spu_two, spu_three, spu_four, spu_five, spu_six, spu_seven,
            spu_eight, spu_nine, spu_ten, spu_eleven, spu_twelve;

    private byte[] sinal_one = {1};
    private byte[] sinal_two = {2};
    private byte[] sinal_three = {3};
    private byte[] sinal_four = {4};
    private byte[] sinal_five = {5};
    private byte[] sinal_six = {6};
    private byte[] sinal_seven = {7};
    private byte[] sinal_eight = {8};
    private byte[] sinal_nine = {9};
    private byte[] sinal_ten = {10};
    private byte[] sinal_eleven = {11};
    private byte[] sinal_twelve = {12};

    TimerTask sendMeesage = new TimerTask() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            sendAllMessage();
        }
    };

    private final MyHandler mHandler = new MyHandler(this);

    public void onClick_config(View view) {
        time_delay = Integer.parseInt(et_time_delay.getText().toString());
        Toast.makeText(this, "设置完成", Toast.LENGTH_SHORT).show();
        //Log.d("time_delay", "=time_delay=>>" + time_delay);
    }

    public void onClick_clear(View view) {
        et_time_delay.setText("");

        tv_ttyS0_send.setText("");
        tv_ttyS0_receive.setText("");
        tv_ttyS1_send.setText("");
        tv_ttyS1_receive.setText("");
        tv_ttyS2_send.setText("");
        tv_ttyS2_receive.setText("");
        tv_ttyS3_send.setText("");
        tv_ttyS3_receive.setText("");

        tv_ttymxc1_send.setText("");
        tv_ttymxc1_receive.setText("");
        tv_ttymxc2_send.setText("");
        tv_ttymxc2_receive.setText("");
        tv_ttymxc3_send.setText("");
        tv_ttymxc3_receive.setText("");
        tv_ttymxc4_send.setText("");
        tv_ttymxc4_receive.setText("");

        tv_ttysWK0_send.setText("");
        tv_ttysWK0_receive.setText("");
        tv_ttysWK1_send.setText("");
        tv_ttysWK1_receive.setText("");
        tv_ttysWK2_send.setText("");
        tv_ttysWK2_receive.setText("");
        tv_ttysWK3_send.setText("");
        tv_ttysWK3_receive.setText("");

        ttyS0_send_size = 0;
        ttyS0_receive_size = 0;
        ttyS1_send_size = 0;
        ttyS1_receive_size = 0;
        ttyS2_send_size = 0;
        ttyS2_receive_size = 0;
        ttyS3_send_size = 0;
        ttyS3_receive_size = 0;

        ttymxc1_send_size = 0;
        ttymxc1_receive_size = 0;
        ttymxc2_send_size = 0;
        ttymxc2_receive_size = 0;
        ttymxc3_send_size = 0;
        ttymxc3_receive_size = 0;
        ttymxc4_send_size = 0;
        ttymxc4_receive_size = 0;

        ttysWK0_send_size = 0;
        ttysWK0_receive_size = 0;
        ttysWK1_send_size = 0;
        ttysWK1_receive_size = 0;
        ttysWK2_send_size = 0;
        ttysWK2_receive_size = 0;
        ttysWK3_send_size = 0;
        ttysWK3_receive_size = 0;
    }

    public void onClick_ping(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                isAvailableByPing("192.168.1.1");
            }
        }).start();
    }

    class MyHandler extends Handler {
        private WeakReference<MainActivity> mActivityRef;

        public MyHandler(MainActivity activity) {
            mActivityRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivityRef.get();
            switch (msg.what) {
                case ttyS0_send:
                    activity.tv_ttyS0_send.setText(String.valueOf(ttyS0_send_size));
                    break;
                case ttyS0_receive:
                    activity.tv_ttyS0_receive.setText(String.valueOf(ttyS0_receive_size));
                    break;
                case ttyS1_send:
                    activity.tv_ttyS1_send.setText(String.valueOf(ttyS1_send_size));
                    break;
                case ttyS1_receive:
                    activity.tv_ttyS1_receive.setText(String.valueOf(ttyS1_receive_size));
                    break;
                case ttyS2_send:
                    activity.tv_ttyS2_send.setText(String.valueOf(ttyS2_send_size));
                    break;
                case ttyS2_receive:
                    activity.tv_ttyS2_receive.setText(String.valueOf(ttyS2_receive_size));
                    break;
                case ttyS3_send:
                    activity.tv_ttyS3_send.setText(String.valueOf(ttyS3_send_size));
                    break;
                case ttyS3_receive:
                    activity.tv_ttyS3_receive.setText(String.valueOf(ttyS3_receive_size));
                    break;

                case ttymxc1_send:
                    activity.tv_ttymxc1_send.setText(String.valueOf(ttymxc1_send_size));
                    break;
                case ttymxc1_receive:
                    activity.tv_ttymxc1_receive.setText(String.valueOf(ttymxc1_receive_size));
                    break;
                case ttymxc2_send:
                    activity.tv_ttymxc2_send.setText(String.valueOf(ttymxc2_send_size));
                    break;
                case ttymxc2_receive:
                    activity.tv_ttymxc2_receive.setText(String.valueOf(ttymxc2_receive_size));
                    break;
                case ttymxc3_send:
                    activity.tv_ttymxc3_send.setText(String.valueOf(ttymxc3_send_size));
                    break;
                case ttymxc3_receive:
                    activity.tv_ttymxc3_receive.setText(String.valueOf(ttymxc3_receive_size));
                    break;
                case ttymxc4_send:
                    activity.tv_ttymxc4_send.setText(String.valueOf(ttymxc4_send_size));
                    break;
                case ttymxc4_receive:
                    activity.tv_ttymxc4_receive.setText(String.valueOf(ttymxc4_receive_size));
                    break;

                case ttysWK0_send:
                    activity.tv_ttysWK0_send.setText(String.valueOf(ttysWK0_send_size));
                    break;
                case ttysWK0_receive:
                    activity.tv_ttysWK0_receive.setText(String.valueOf(ttysWK0_receive_size));
                    break;
                case ttysWK1_send:
                    activity.tv_ttysWK1_send.setText(String.valueOf(ttysWK1_send_size));
                    break;
                case ttysWK1_receive:
                    activity.tv_ttysWK1_receive.setText(String.valueOf(ttysWK1_receive_size));
                    break;
                case ttysWK2_send:
                    activity.tv_ttysWK2_send.setText(String.valueOf(ttysWK2_send_size));
                    break;
                case ttysWK2_receive:
                    activity.tv_ttysWK2_receive.setText(String.valueOf(ttysWK2_receive_size));
                    break;
                case ttysWK3_send:
                    activity.tv_ttysWK3_send.setText(String.valueOf(ttysWK3_send_size));
                    break;
                case ttysWK3_receive:
                    activity.tv_ttysWK3_receive.setText(String.valueOf(ttysWK3_receive_size));
                    break;

                case PING_RESULT:
                    activity.tv_ping_result.setText(msg.obj.toString());
                    break;


            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        vv_play = (VideoView) findViewById(R.id.vv_testVideo);
        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dazhong);
        playVideo();

        initViews();

        openSerialPort();
    }

    private void initViews() {
        //ping 结果
        tv_ping_result = findViewById(R.id.tv_ping_result);
        //跑马灯
        tv_marquee = findViewById(R.id.tv_marquee);
        tv_marquee.setSelected(true);

        et_time_delay = findViewById(R.id.et_time_delay);

        btn_ttyALL_onff = findViewById(R.id.btn_ttyALL_onff);

        btn_ttyS0_onff = findViewById(R.id.btn_ttyS0_onff);
        btn_ttyS1_onff = findViewById(R.id.btn_ttyS1_onff);
        btn_ttyS2_onff = findViewById(R.id.btn_ttyS2_onff);
        btn_ttyS3_onff = findViewById(R.id.btn_ttyS3_onff);
        tv_ttyS0_send = findViewById(R.id.tv_ttyS0_send);
        tv_ttyS1_send = findViewById(R.id.tv_ttyS1_send);
        tv_ttyS2_send = findViewById(R.id.tv_ttyS2_send);
        tv_ttyS3_send = findViewById(R.id.tv_ttyS3_send);
        tv_ttyS0_receive = findViewById(R.id.tv_ttyS0_receive);
        tv_ttyS1_receive = findViewById(R.id.tv_ttyS1_receive);
        tv_ttyS2_receive = findViewById(R.id.tv_ttyS2_receive);
        tv_ttyS3_receive = findViewById(R.id.tv_ttyS3_receive);

        btn_ttymxc1_onff = findViewById(R.id.btn_ttymxc1_onff);
        btn_ttymxc2_onff = findViewById(R.id.btn_ttymxc2_onff);
        btn_ttymxc3_onff = findViewById(R.id.btn_ttymxc3_onff);
        btn_ttymxc4_onff = findViewById(R.id.btn_ttymxc4_onff);
        tv_ttymxc1_send = findViewById(R.id.tv_ttymxc1_send);
        tv_ttymxc2_send = findViewById(R.id.tv_ttymxc2_send);
        tv_ttymxc3_send = findViewById(R.id.tv_ttymxc3_send);
        tv_ttymxc4_send = findViewById(R.id.tv_ttymxc4_send);
        tv_ttymxc1_receive = findViewById(R.id.tv_ttymxc1_receive);
        tv_ttymxc2_receive = findViewById(R.id.tv_ttymxc2_receive);
        tv_ttymxc3_receive = findViewById(R.id.tv_ttymxc3_receive);
        tv_ttymxc4_receive = findViewById(R.id.tv_ttymxc4_receive);

        btn_ttysWK0_onff = findViewById(R.id.btn_ttysWK0_onff);
        btn_ttysWK1_onff = findViewById(R.id.btn_ttysWK1_onff);
        btn_ttysWK2_onff = findViewById(R.id.btn_ttysWK2_onff);
        btn_ttysWK3_onff = findViewById(R.id.btn_ttysWK3_onff);
        tv_ttysWK0_send = findViewById(R.id.tv_ttysWK0_send);
        tv_ttysWK1_send = findViewById(R.id.tv_ttysWK1_send);
        tv_ttysWK2_send = findViewById(R.id.tv_ttysWK2_send);
        tv_ttysWK3_send = findViewById(R.id.tv_ttysWK3_send);
        tv_ttysWK0_receive = findViewById(R.id.tv_ttysWK0_receive);
        tv_ttysWK1_receive = findViewById(R.id.tv_ttysWK1_receive);
        tv_ttysWK2_receive = findViewById(R.id.tv_ttysWK2_receive);
        tv_ttysWK3_receive = findViewById(R.id.tv_ttysWK3_receive);
    }


    private void playVideo() {
        vv_play.setVideoURI(uri);
        vv_play.requestFocus();
        vv_play.start();
        vv_play.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setLooping(true);
            }
        });

        vv_play.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
                vv_play.start();
                vv_play.seekTo(0);
                return false;
            }
        });
    }

    public void sendAllMessage() {
        spu_one.sendBuffer(sinal_one);
        spu_two.sendBuffer(sinal_two);
        spu_three.sendBuffer(sinal_three);
        spu_four.sendBuffer(sinal_four);
        if (spu_five != null) {
            spu_five.sendBuffer(sinal_five);
        }
        if (spu_six != null) {
            spu_six.sendBuffer(sinal_six);
        }
        if (spu_seven != null) {
            spu_seven.sendBuffer(sinal_seven);
        }
        if (spu_eight != null) {
            spu_eight.sendBuffer(sinal_eight);
        }
        if (spu_nine != null) {
            spu_nine.sendBuffer(sinal_nine);
            ttyS0_send_size += sinal_nine.length;
            mHandler.sendEmptyMessage(ttyS0_send);
        }
        if (spu_ten != null) {
            spu_ten.sendBuffer(sinal_ten);
            ttyS1_send_size += sinal_ten.length;
            mHandler.sendEmptyMessage(ttyS1_send);
        }
        if (spu_eleven != null) {
            spu_eleven.sendBuffer(sinal_eleven);
            ttyS2_send_size += sinal_eleven.length;
            mHandler.sendEmptyMessage(ttyS2_send);
        }
        if (spu_twelve != null) {
            spu_twelve.sendBuffer(sinal_twelve);
            ttyS3_send_size += sinal_eleven.length;
            mHandler.sendEmptyMessage(ttyS3_send);
        }
    }

    public void openSerialPort() {
        //ttymxc1 - ttymxc4
        spu_one = new SerialPortUtil(Param.SPU_ONE_PATH, Param.BAUDRATE);
        spu_two = new SerialPortUtil(Param.SPU_TWO_PATH, Param.BAUDRATE);
        spu_three = new SerialPortUtil(Param.SPU_THREE_PATH, Param.BAUDRATE);
        spu_four = new SerialPortUtil(Param.SPU_FOUR_PATH, Param.BAUDRATE);
        //ttysWK0 - ttysWK3
        spu_five = new SerialPortUtil(Param.SPU_FIVE_PATH, Param.BAUDRATE);
        spu_six = new SerialPortUtil(Param.SPU_SIX_PATH, Param.BAUDRATE);
        spu_seven = new SerialPortUtil(Param.SPU_SEVEN_PATH, Param.BAUDRATE);
        spu_eight = new SerialPortUtil(Param.SPU_EIGHT_PATH, Param.BAUDRATE);
        //ttyS0 - ttyS3
        spu_nine = new SerialPortUtil(Param.SPU_NINE_PATH, Param.BAUDRATE);
        spu_ten = new SerialPortUtil(Param.SPU_TEN_PATH, Param.BAUDRATE);
        spu_eleven = new SerialPortUtil(Param.SPU_ELEVEN_PATH, Param.BAUDRATE);
        spu_twelve = new SerialPortUtil(Param.SPU_TWELVE_PATH, Param.BAUDRATE);

        spu_one.setOnDataReceiveListener(new OnDataReceiveListener() {

            @Override
            public void onDataReceive(byte[] buffer, int size) {
                // TODO Auto-generated method stub
                int i = 0;
                if (buffer.length > 0) {
                    i = buffer[0] & 0xFF;
                    ttymxc1_receive_size += size;
                    mHandler.sendEmptyMessage(ttymxc1_receive);
                    //LogUtil.d(TAG, "spu_one receive ===>" + i);
                }
            }
        });

        spu_two.setOnDataReceiveListener(new OnDataReceiveListener() {

            @Override
            public void onDataReceive(byte[] buffer, int size) {
                // TODO Auto-generated method stub
                int i = 0;
                if (buffer.length > 0) {
                    i = buffer[0] & 0xFF;
                    ttymxc2_receive_size += size;
                    mHandler.sendEmptyMessage(ttymxc2_receive);
                    //LogUtil.d(TAG, "spu_two receive ===>" + i);
                }

            }
        });

        spu_three.setOnDataReceiveListener(new OnDataReceiveListener() {

            @Override
            public void onDataReceive(byte[] buffer, int size) {
                // TODO Auto-generated method stub
                int i = 0;
                if (buffer.length > 0) {
                    i = buffer[0] & 0xFF;
                    ttymxc3_receive_size += size;
                    mHandler.sendEmptyMessage(ttymxc3_receive);
                    //LogUtil.d(TAG, "spu_three receive ===>" + i);
                }
            }
        });

        spu_four.setOnDataReceiveListener(new OnDataReceiveListener() {

            @Override
            public void onDataReceive(byte[] buffer, int size) {
                // TODO Auto-generated method stub
                int i = 0;
                if (buffer.length > 0) {
                    i = buffer[0] & 0xFF;
                    ttymxc4_receive_size += size;
                    mHandler.sendEmptyMessage(ttymxc4_receive);
                    //LogUtil.d(TAG, "spu_four receive ===>" + i);
                }
            }
        });


        if (spu_five != null) {
            spu_five.setOnDataReceiveListener(new OnDataReceiveListener() {

                @Override
                public void onDataReceive(byte[] buffer, int size) {
                    // TODO Auto-generated method stub
                    int i = 0;
                    if (buffer.length > 0) {
                        i = buffer[0] & 0xFF;
                        ttysWK0_receive_size += size;
                        mHandler.sendEmptyMessage(ttysWK0_receive);
                        //LogUtil.d(TAG, "spu_five receive ===>" + i);
                    }

                }
            });
        }

        if (spu_six != null) {
            spu_six.setOnDataReceiveListener(new OnDataReceiveListener() {

                @Override
                public void onDataReceive(byte[] buffer, int size) {
                    // TODO Auto-generated method stub
                    int i = 0;
                    if (buffer.length > 0) {
                        i = buffer[0] & 0xFF;
                        ttysWK1_receive_size += size;
                        mHandler.sendEmptyMessage(ttysWK1_receive);
                        //LogUtil.d(TAG, "spu_six receive ===>" + i);
                    }

                }
            });
        }

        if (spu_seven != null) {
            spu_seven.setOnDataReceiveListener(new OnDataReceiveListener() {

                @Override
                public void onDataReceive(byte[] buffer, int size) {
                    // TODO Auto-generated method stub
                    int i = 0;
                    if (buffer.length > 0) {
                        i = buffer[0] & 0xFF;
                        ttysWK2_receive_size += size;
                        mHandler.sendEmptyMessage(ttysWK2_receive);
                        //LogUtil.d(TAG, "spu_seven receive ===>" + i);
                    }

                }
            });
        }

        if (spu_eight != null) {
            spu_eight.setOnDataReceiveListener(new OnDataReceiveListener() {

                @Override
                public void onDataReceive(byte[] buffer, int size) {
                    // TODO Auto-generated method stub
                    int i = 0;
                    if (buffer.length > 0) {
                        i = buffer[0] & 0xFF;
                        ttysWK3_receive_size += size;
                        mHandler.sendEmptyMessage(ttysWK3_receive);
                        //LogUtil.d(TAG, "spu_eight receive ===>" + i);
                    }

                }
            });
        }

        if (spu_nine != null) {
            spu_nine.setOnDataReceiveListener(new OnDataReceiveListener() {

                @Override
                public void onDataReceive(byte[] buffer, int size) {
                    // TODO Auto-generated method stub
                    int i = 0;
                    if (buffer.length > 0) {
                        i = buffer[0] & 0xFF;
                        ttyS0_receive_size += size;
                        mHandler.sendEmptyMessage(ttyS0_receive);
                        //LogUtil.d(TAG, "spu_nine receive ===>" + i);
                    }

                }
            });
        }

        if (spu_ten != null) {
            spu_ten.setOnDataReceiveListener(new OnDataReceiveListener() {

                @Override
                public void onDataReceive(byte[] buffer, int size) {
                    // TODO Auto-generated method stub
                    int i = 0;
                    if (buffer.length > 0) {
                        i = buffer[0] & 0xFF;
                        ttyS1_receive_size += size;
                        mHandler.sendEmptyMessage(ttyS1_receive);
                        //LogUtil.d(TAG, "spu_ten receive ===>" + i);
                    }

                }
            });
        }

        if (spu_eleven != null) {
            spu_eleven.setOnDataReceiveListener(new OnDataReceiveListener() {

                @Override
                public void onDataReceive(byte[] buffer, int size) {
                    // TODO Auto-generated method stub
                    int i = 0;
                    if (size > 0) {
                        i = buffer[0] & 0xFF;
                        ttyS2_receive_size += size;
                        mHandler.sendEmptyMessage(ttyS2_receive);
                        //LogUtil.d(TAG, "spu_eleven receive ===>" + ttyS0_send_size + "=buffer.length=>>" + buffer.length);
                    }
                }
            });
        }

        if (spu_twelve != null) {
            spu_twelve.setOnDataReceiveListener(new OnDataReceiveListener() {

                @Override
                public void onDataReceive(byte[] buffer, int size) {
                    // TODO Auto-generated method stub
                    int i = 0;
                    if (buffer.length > 0) {
                        i = buffer[0] & 0xFF;
                        ttyS3_receive_size += size;
                        mHandler.sendEmptyMessage(ttyS3_receive);
                        //LogUtil.d(TAG, "spu_twelve receive ===>" + i);
                    }

                }
            });
        }

    }

    public void onClick_ttys_ALL(View view) {
        if (ttyALL_exit) {
            ttyALL_exit = false;
            btn_ttyALL_onff.setText("全部停止");
        } else {
            ttyALL_exit = true;
            btn_ttyALL_onff.setText("全部开始");
        }
        btn_ttyS0_onff.performClick();
        btn_ttyS1_onff.performClick();
        btn_ttyS2_onff.performClick();
        btn_ttyS3_onff.performClick();

        btn_ttymxc1_onff.performClick();
        btn_ttymxc2_onff.performClick();
        btn_ttymxc3_onff.performClick();
        btn_ttymxc4_onff.performClick();

        btn_ttysWK0_onff.performClick();
        btn_ttysWK1_onff.performClick();
        btn_ttysWK2_onff.performClick();
        btn_ttysWK3_onff.performClick();
    }

    public void onClick_ttyS0(View view) {
        if (ttyS0_exit) {
            ttyS0_exit = false;
            btn_ttyS0_onff.setText("ttyS0 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttyS0_exit) {
                        if (spu_nine != null) {
                            spu_nine.sendBuffer(sinal_nine);
                            ttyS0_send_size += sinal_nine.length;
                            SystemClock.sleep(time_delay);
                            mHandler.sendEmptyMessage(ttyS0_send);
                        }
                    }
                }
            }).start();
        } else {
            ttyS0_exit = true;
            btn_ttyS0_onff.setText("ttyS0 开");
        }
    }


    public void onClick_ttyS1(View view) {
        if (ttyS1_exit) {
            ttyS1_exit = false;
            btn_ttyS1_onff.setText("ttyS1 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttyS1_exit) {
                        if (spu_ten != null) {
                            spu_ten.sendBuffer(sinal_ten);
                            SystemClock.sleep(time_delay);
                            ttyS1_send_size += sinal_ten.length;
                            mHandler.sendEmptyMessage(ttyS1_send);
                        }
                    }
                }
            }).start();
        } else {
            ttyS1_exit = true;
            btn_ttyS1_onff.setText("ttyS1 开");
        }
    }

    public void onClick_ttyS2(View view) {
        if (ttyS2_exit) {
            ttyS2_exit = false;
            btn_ttyS2_onff.setText("ttyS2 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttyS2_exit) {
                        if (spu_eleven != null) {
                            spu_eleven.sendBuffer(sinal_eleven);
                            SystemClock.sleep(time_delay);
                            ttyS2_send_size += sinal_eleven.length;
                            mHandler.sendEmptyMessage(ttyS2_send);
                        }
                    }
                }
            }).start();
        } else {
            ttyS2_exit = true;
            btn_ttyS2_onff.setText("ttyS2 开");
        }
    }

    public void onClick_ttyS3(View view) {
        if (ttyS3_exit) {
            ttyS3_exit = false;
            btn_ttyS3_onff.setText("ttyS3 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttyS3_exit) {
                        if (spu_twelve != null) {
                            spu_twelve.sendBuffer(sinal_twelve);
                            SystemClock.sleep(time_delay);
                            ttyS3_send_size += sinal_twelve.length;
                            mHandler.sendEmptyMessage(ttyS3_send);
                        }
                    }
                }
            }).start();
        } else {
            ttyS3_exit = true;
            btn_ttyS3_onff.setText("ttyS3 开");
        }
    }

    public void onClick_ttymxc1(View view) {
        if (ttymxc1_exit) {
            ttymxc1_exit = false;
            btn_ttymxc1_onff.setText("ttymxc1 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttymxc1_exit) {
                        if (spu_one != null) {
                            spu_one.sendBuffer(sinal_one);
                            SystemClock.sleep(time_delay);
                            ttymxc1_send_size += sinal_one.length;
                            mHandler.sendEmptyMessage(ttymxc1_send);
                        }
                    }
                }
            }).start();
        } else {
            ttymxc1_exit = true;
            btn_ttymxc1_onff.setText("ttymxc1 开");
        }
    }

    public void onClick_ttymxc2(View view) {
        if (ttymxc2_exit) {
            ttymxc2_exit = false;
            btn_ttymxc2_onff.setText("ttymxc2 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttymxc2_exit) {
                        if (spu_two != null) {
                            spu_two.sendBuffer(sinal_two);
                            SystemClock.sleep(time_delay);
                            ttymxc2_send_size += sinal_two.length;
                            mHandler.sendEmptyMessage(ttymxc2_send);
                        }
                    }
                }
            }).start();
        } else {
            ttymxc2_exit = true;
            btn_ttymxc2_onff.setText("ttymxc2 开");
        }
    }

    public void onClick_ttymxc3(View view) {
        if (ttymxc3_exit) {
            ttymxc3_exit = false;
            btn_ttymxc3_onff.setText("ttymxc3 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttymxc3_exit) {
                        if (spu_three != null) {
                            spu_three.sendBuffer(sinal_three);
                            SystemClock.sleep(time_delay);
                            ttymxc3_send_size += sinal_three.length;
                            mHandler.sendEmptyMessage(ttymxc3_send);
                        }
                    }
                }
            }).start();
        } else {
            ttymxc3_exit = true;
            btn_ttymxc3_onff.setText("ttymxc3 开");
        }
    }

    public void onClick_ttymxc4(View view) {
        if (ttymxc4_exit) {
            ttymxc4_exit = false;
            btn_ttymxc4_onff.setText("ttymxc4 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttymxc4_exit) {
                        if (spu_four != null) {
                            spu_four.sendBuffer(sinal_four);
                            SystemClock.sleep(time_delay);
                            ttymxc4_send_size += sinal_four.length;
                            mHandler.sendEmptyMessage(ttymxc4_send);
                        }
                    }
                }
            }).start();
        } else {
            ttymxc4_exit = true;
            btn_ttymxc4_onff.setText("ttymxc4 开");
        }
    }

    public void onClick_ttysWK0(View view) {
        if (ttysWK0_exit) {
            ttysWK0_exit = false;
            btn_ttysWK0_onff.setText("ttysWK0 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttysWK0_exit) {
                        if (spu_five != null) {
                            spu_five.sendBuffer(sinal_five);
                            SystemClock.sleep(time_delay);
                            ttysWK0_send_size += sinal_five.length;
                            mHandler.sendEmptyMessage(ttysWK0_send);
                        }
                    }
                }
            }).start();
        } else {
            ttysWK0_exit = true;
            btn_ttysWK0_onff.setText("ttysWK0 开");
        }
    }

    public void onClick_ttysWK1(View view) {
        if (ttysWK1_exit) {
            ttysWK1_exit = false;
            btn_ttysWK1_onff.setText("ttysWK1 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttysWK1_exit) {
                        if (spu_six != null) {
                            spu_six.sendBuffer(sinal_six);
                            SystemClock.sleep(time_delay);
                            ttysWK1_send_size += sinal_six.length;
                            mHandler.sendEmptyMessage(ttysWK1_send);
                        }
                    }
                }
            }).start();
        } else {
            ttysWK1_exit = true;
            btn_ttysWK1_onff.setText("ttysWK1 开");
        }
    }

    public void onClick_ttysWK2(View view) {
        if (ttysWK2_exit) {
            ttysWK2_exit = false;
            btn_ttysWK2_onff.setText("ttysWK2 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttysWK2_exit) {
                        if (spu_seven != null) {
                            spu_seven.sendBuffer(sinal_seven);
                            SystemClock.sleep(time_delay);
                            ttysWK2_send_size += sinal_seven.length;
                            mHandler.sendEmptyMessage(ttysWK2_send);
                        }
                    }
                }
            }).start();
        } else {
            ttysWK2_exit = true;
            btn_ttysWK2_onff.setText("ttysWK2 开");
        }
    }

    public void onClick_ttysWK3(View view) {
        if (ttysWK3_exit) {
            ttysWK3_exit = false;
            btn_ttysWK3_onff.setText("ttysWK3 关");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!ttysWK3_exit) {
                        if (spu_eight != null) {
                            spu_eight.sendBuffer(sinal_eight);
                            SystemClock.sleep(time_delay);
                            ttysWK3_send_size += sinal_eight.length;
                            mHandler.sendEmptyMessage(ttysWK3_send);
                        }
                    }
                }
            }).start();
        } else {
            ttysWK3_exit = true;
            btn_ttysWK3_onff.setText("ttysWK3 开");
        }
    }

    public boolean isAvailableByPing(String ip) {
        if ((ip == null) || (ip.length() <= 0)) {
            ip = "192.168.1.1";
        }
        Runtime runtime = Runtime.getRuntime();
        Process ipProcess = null;
        BufferedReader bufferedReader = null;
        String line = null;
        try {
            //-c 后边跟随的是重复的次数，-w后边跟随的是超时的时间，单位是秒，不是毫秒，要不然也不会anr了
            ipProcess = runtime.exec("ping " + ip);
            bufferedReader = new BufferedReader(new InputStreamReader(ipProcess.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                Log.d("ping", "=line=>>" + line);
                Message msg = Message.obtain();
                msg.what = PING_RESULT;
                msg.obj = line;
                mHandler.sendMessage(msg);
            }
            int exitValue = ipProcess.waitFor();
            Log.d("ping", "Process:" + exitValue);
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //在结束的时候应该对资源进行回收
            if (ipProcess != null) {
                ipProcess.destroy();
            }
            runtime.gc();
        }
        return false;
    }

}
