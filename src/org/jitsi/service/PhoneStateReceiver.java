package org.jitsi.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;
import net.java.sip.communicator.service.gui.call.CallRenderer;
import net.java.sip.communicator.service.protocol.Call;
import net.java.sip.communicator.util.Logger;
import net.java.sip.communicator.util.call.CallManager;
import org.jitsi.R;
import org.jitsi.android.JitsiApplication;
import org.jitsi.android.gui.call.CallVolumeCtrlFragment;
import org.jitsi.android.gui.call.notification.CallControl;
import org.jitsi.android.gui.chat.ChatSession;
import org.jitsi.android.gui.contactlist.ContactListFragment;
import org.jitsi.impl.neomedia.jmfext.media.renderer.audio.AudioTrackRenderer;
import org.jitsi.impl.neomedia.jmfext.media.renderer.audio.OpenSLESRenderer;

import java.util.*;


public class PhoneStateReceiver extends Service {

    private static final int INTERVAL = 3000; // poll every 3 secs

    SharedPreferences sharedpreferences;
    private static Timer timer;
    private static Timer servicetime;
    private static TimerTask servicetask, disconnect;
    Collection<Call> collection;


    /**
     * The logger
     */
    private static final Logger logger
            = Logger.getLogger(PhoneStateReceiver.class);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        logger.info("jitsi is in service started oncreate");
        sharedpreferences = getSharedPreferences("preference", Context.MODE_PRIVATE);
        servicetime = new Timer();
        long delays = 2000;
        long intervals = 3000;
        servicetask = new TimerTask() {
            @Override
            public void run() {

                //getting time in second to disconnect call when application is in background form sharedpreferenc
                String secondtodisconnect = sharedpreferences.getString(JitsiApplication.getResString(R.string.pref_key_call_disconnect), "20");
                Long timetodisconnet = Long.valueOf(secondtodisconnect);
                logger.info("jitsi is in foreground " +" thread id "+Thread.currentThread().getName()+ " " + secondtodisconnect + " " + timetodisconnet + " listen " + ContactListFragment.listen_flag);

                //collection contains all the current calls
                collection = CallManager.getActiveCalls();
                //condition to check jitsi in background && there is a call

                if (!collection.isEmpty()) {
                    if (ContactListFragment.listen_flag) {
                        try {
                            TextView status;
                            status = (TextView) JitsiApplication.getCurrentActivity().findViewById(R.id.callStatus);
                            logger.info("jitsi is call is mute " + status.getText());
                            if (status.getText().toString().contains("Connected")) {
                                Iterator<Call> iterator = collection.iterator();
                                // while loop
                                while (iterator.hasNext()) {
                                    logger.info("jitsi is call is mute " + CallManager.isMute(iterator.next()));
                                    CallManager.setMute(iterator.next(), true);
                                }
                            }
                        }
                        catch (Exception e){
                            logger.info("jitsi is call is mute error " +e.getMessage());
                        }
                    } else {

                    }
                    if (!isJitsiinforeground()) {
                        // todo hangup call in 30sec
                        // CallManager.hangupCall(call);
                        logger.info("jitsi is in background and has call");
                        //mychange timertask to end call when call is in background after given delay
                        timer = new Timer();
                        long delay = timetodisconnet * 1000;

                        //mute call
                        Iterator<Call> iterator = collection.iterator();
                        // while loop
                        while (iterator.hasNext()) {
                            CallManager.setMute(iterator.next(), true);
                            ChatSession.sendMessage("mute");

                            /*AudioManager audio
                                    = (AudioManager) JitsiApplication.getGlobalContext()
                                    .getSystemService(Context.AUDIO_SERVICE);
                            try{
                                audio.setSpeakerphoneOn(false);
                                audio.setMicrophoneMute(false);
                            }
                            catch (Exception e){
                                logger.info("jitsi is in background and has call\")jitsi is in background and has call\") and "+e.getMessage());

                            }*/

                            /*AudioTrackRenderer audioTrackRenderer = new AudioTrackRenderer(true);
                            audioTrackRenderer.stop();*/
                            /*OpenSLESRenderer openSLESRenderer = new OpenSLESRenderer();
                            openSLESRenderer.stop();*/
                            // CallManager.putOnHold(iterator.next(),true);
                        }

                        // schedules the task to be run in an interval
                        disconnect = new TimerTask() {
                            @Override
                            public void run() {

                                logger.info("jitsi is in timetask started");
                                // task to run goes here
                                if (!isJitsiinforeground()) {
                                    Iterator<Call> iterator = collection.iterator();
                                    // while loop
                                    while (iterator.hasNext()) {
                                        CallManager.hangupCall(iterator.next());
                                        //CallManager.setMute(iterator.next(), true);
                                    }
                                }
                                try {
                                    logger.info("canceling disconnect " +" thread id "+Thread.currentThread().getName());

                                    timer.cancel();
                                } catch (Exception e) {

                                }
                                try {
                                    timer.purge();

                                } catch (Exception e) {

                                }
                                //onDestroy();
                                logger.info("jitsi is in timetask stopped");                            }
                        };
                        timer.schedule(disconnect, delay);
                    } else {
                        logger.info("jitsi is in foreground and has call");
                        ChatSession.sendMessage("talk");
                        if (!ContactListFragment.listen_flag) {
                            Iterator<Call> iterator = collection.iterator();
                            // while loop
                            while (iterator.hasNext()) {
                                // UnMuteAudio();
                                CallManager.setMute(iterator.next(), false);
                            }
                        }
                        // onDestroy();
                    }
                } else {
                    /*try {
                        logger.info("caught ex thread id " + Thread.currentThread().getName());
                        Thread.sleep(20000);
                    } catch(InterruptedException ex) {
                        logger.info("caught ex" + ex.getMessage());
                    }*/
                    try {
                        logger.info("canceling servertime " +" thread id "+Thread.currentThread().getName());

                        servicetime.cancel();
                    } catch (Exception e) {

                    }
                    try {
                        servicetime.purge();

                    } catch (Exception e) {

                    }
                    // onDestroy();
                }
                // handler.postDelayed(runnable, INTERVAL);
            }
        };
        servicetime.schedule(servicetask, delays, intervals);
        /*handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                //getting time in second to disconnect call when application is in background form sharedpreferenc
                String secondtodisconnect = sharedpreferences.getString(JitsiApplication.getResString(R.string.pref_key_call_disconnect), "20");
                Long timetodisconnet = Long.valueOf(secondtodisconnect);
                logger.info("jitsi is in foreground " + secondtodisconnect + " " + timetodisconnet);

                //collection contains all the current calls
                final Collection<Call> collection = CallManager.getActiveCalls();
                //condition to check jitsi in background && there is a call

                if (!collection.isEmpty()) {
                    if (!isJitsiinforeground()) {
                        // todo hangup call in 30sec
                        // CallManager.hangupCall(call);
                        logger.info("jitsi is in background and has call");
                        //mychange timertask to end call when call is in background after given delay
                        timer = new Timer();
                        long delay = timetodisconnet * 1000;

                        //mute call
                        Iterator<Call> iterator = collection.iterator();
                        // while loop
                        while (iterator.hasNext()) {
                            CallManager.setMute(iterator.next(), true);
                        }

                        // schedules the task to be run in an interval
                        TimerTask disconnect = new TimerTask() {
                            @Override
                            public void run() {

                                logger.info("jitsi is in timetask started");
                                // task to run goes here
                                if (!isJitsiinforeground()) {
                                    Iterator<Call> iterator = collection.iterator();
                                    // while loop
                                    while (iterator.hasNext()) {
                                        CallManager.hangupCall(iterator.next());
                                        CallManager.setMute(iterator.next(), true);
                                    }
                                }
                                try{
                                    timer.cancel();
                                    timer.purge();

                                }
                                catch (Exception e){

                                }
                                logger.info("jitsi is in timetask stopped");
                            }
                        };
                        timer.schedule(disconnect, delay);
                    } else {

                        logger.info("jitsi is in foreground and has call");
                        Iterator<Call> iterator = collection.iterator();
                        // while loop
                        while (iterator.hasNext()) {
                            CallManager.setMute(iterator.next(), false);
                        }
                        onDestroy();
                    }
                } else {
                    onDestroy();

                }

                handler.postDelayed(runnable, INTERVAL);

            }
        };
        handler.postDelayed(runnable, INTERVAL);*/

    }

    public boolean isJitsiinforeground() {
        try {
            if ((JitsiApplication.getCurrentActivity().toString()).contains("jitsi")) {
                return true;
            }
            logger.info("Current activity is " + JitsiApplication.getCurrentActivity());
            return false;
        } catch (Exception e) {
            logger.info("Current activity Exception is " + e.getMessage());
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.info("jitsi is in service stopped");
        try {
            logger.info("caught ex  id " + Thread.currentThread().getName());
            // Thread.sleep(20000);
        } catch(Exception ex) {
            logger.info("caught ex" + ex.getMessage());
        }
        try {
            servicetime.cancel();
        } catch (Exception e) {
            logger.info("jitsi is in servicetime cancel error " + e.getMessage());
        }
        try {
            servicetime.purge();
        } catch (Exception e) {
            logger.info("jitsi is in servicetime purge error " + e.getMessage());
        }

        try {
            servicetask.cancel();
        } catch (Exception e) {
            logger.info("jitsi is in servicetask cancel error " + e.getMessage());
        }
        //handler.removeCallbacks(runnable);
        // handler.removeCallbacks(null);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.info("jitsi is in service started onstartcommand");
        onCreate();
        return super.onStartCommand(intent, flags, startId);
    }

    public void MuteAudio(){
        AudioManager mAlramMAnager = (AudioManager) JitsiApplication.getCurrentActivity().getSystemService(JitsiApplication.getGlobalContext().AUDIO_SERVICE);
        mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
        mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, true);
        mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, true);
        mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
    }

    public void UnMuteAudio(){
        AudioManager mAlramMAnager = (AudioManager) JitsiApplication.getCurrentActivity().getSystemService(JitsiApplication.getGlobalContext().AUDIO_SERVICE);

        mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
        mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, false);
        mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, false);
        mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, false);

    }

}