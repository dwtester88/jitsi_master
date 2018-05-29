/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Copyright @ 2015 Atlassian Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jitsi.android.gui.chat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Environment;
import android.text.Layout;
import android.util.Base64;
import android.view.View;
import android.widget.*;
import net.java.sip.communicator.service.contactlist.*;
import net.java.sip.communicator.service.protocol.*;
import net.java.sip.communicator.service.protocol.event.*;
import net.java.sip.communicator.service.replacement.*;
import net.java.sip.communicator.service.replacement.smilies.*;
import net.java.sip.communicator.util.*;

import org.jitsi.*;
import org.jitsi.android.*;
import org.jitsi.android.gui.*;
import org.jitsi.android.gui.contactlist.ContactListFragment;
import org.jitsi.android.gui.util.*;
import org.jitsi.service.configuration.*;

/**
 * The <tt>ChatMessageImpl</tt> class encapsulates message information in order
 * to provide a single object containing all data needed to display a chat
 * message.
 * 
 * @author Yana Stamcheva
 * @author Pawel Domas
 */
public class ChatMessageImpl
    implements ChatMessage
{
    /**
     * The logger
     */
    private static final Logger logger
        = Logger.getLogger(ChatMessageImpl.class);

    /**
     * The name of the contact sending the message.
     */
    private final String contactName;

    /**
     * The display name of the contact sending the message.
     */
    private String contactDisplayName;

    /**
     * The date and time of the message.
     */
    private final Date date;

    /**
     * The type of the message.
     */
    private int messageType;

    /**
     * The content of the message.
     */
    private String message;

    /**
     * The content type of the message.
     */
    private String contentType;

    /**
     * A unique identifier for this message.
     */
    private String messageUID;

    /**
     * The unique identifier of the message that this message should replace,
     * or <tt>null</tt> if this is a new message.
     */
    private String correctedMessageUID;

    /**
     * Field used to cache processed message body after replacements and
     * corrections. This text is used to display the message on the screen.
     */
    private String cachedOutput = null;



    /**
     * Creates a <tt>ChatMessageImpl</tt> by specifying all parameters of the
     * message.
     * @param contactName the name of the contact
     * @param date the date and time
     * @param messageType the type (INCOMING or OUTGOING)
     * @param message the content
     * @param contentType the content type (e.g. "text", "text/html", etc.)
     */
    public ChatMessageImpl( String contactName,
                            Date date,
                            int messageType,
                            String message,
                            String contentType)
    {
        this(contactName, null, date, messageType,
                null, message, contentType, null, null);
    }

    /**
     * Creates a <tt>ChatMessageImpl</tt> by specifying all parameters of the
     * message.
     * @param contactName the name of the contact
     * @param date the date and time
     * @param messageType the type (INCOMING or OUTGOING)
     * @param messageTitle the title of the message
     * @param message the content
     * @param contentType the content type (e.g. "text", "text/html", etc.)
     */
    public ChatMessageImpl( String contactName,
                            Date date,
                            int messageType,
                            String messageTitle,
                            String message,
                            String contentType)
    {
        this(contactName, null, date, messageType,
                messageTitle, message, contentType, null, null);
    }

    /**
     * Creates a <tt>ChatMessageImpl</tt> by specifying all parameters of the
     * message.
     * @param contactName the name of the contact
     * @param contactDisplayName the contact display name
     * @param date the date and time
     * @param messageType the type (INCOMING or OUTGOING)
     * @param message the content
     * @param contentType the content type (e.g. "text", "text/html", etc.)
     */
    public ChatMessageImpl( String contactName,
                            String contactDisplayName,
                            Date date,
                            int messageType,
                            String message,
                            String contentType)
    {
        this(contactName, contactDisplayName, date, messageType,
                null, message, contentType, null, null);
    }

    /**
     * Creates a <tt>ChatMessageImpl</tt> by specifying all parameters of the
     * message.
     * @param contactName the name of the contact
     * @param contactDisplayName the contact display name
     * @param date the date and time
     * @param messageType the type (INCOMING or OUTGOING)
     * @param messageTitle the title of the message
     * @param message the content
     * @param contentType the content type (e.g. "text", "text/html", etc.)
     * @param messageUID The ID of the message.
     * @param correctedMessageUID The ID of the message being replaced.
     */
    @SuppressWarnings("unused")
    public ChatMessageImpl(String contactName,
                           String contactDisplayName,
                           Date date,
                           int messageType,
                           String messageTitle,
                           String message,
                           String contentType,
                           String messageUID,
                           String correctedMessageUID)
    {
        this.contactName = contactName;
        this.contactDisplayName = contactDisplayName;
        this.date = date;
        this.messageType = messageType;
        this.message = message;
        this.contentType = contentType;
        this.messageUID = messageUID;
        this.correctedMessageUID = correctedMessageUID;
    }

    /**
     * Returns the name of the contact sending the message.
     * 
     * @return the name of the contact sending the message.
     */
    @Override
    public String getContactName()
    {
        return contactName;
    }

    /**
     * Returns the display name of the contact sending the message.
     *
     * @return the display name of the contact sending the message
     */
    @Override
    public String getContactDisplayName()
    {
        return contactDisplayName;
    }

    /**
     * Returns the date and time of the message.
     * 
     * @return the date and time of the message.
     */
    @Override
    public Date getDate()
    {
        return date;
    }

    /**
     * Returns the type of the message.
     * 
     * @return the type of the message.
     */
    @Override
    public int getMessageType()
    {
        return messageType;
    }

    /**
     * Returns the content of the message.
     * 
     * @return the content of the message.
     */
    @Override
    public String getMessage()
    {
        if(cachedOutput != null)
            return cachedOutput;

        String output = message;

        // Escape HTML content
        if(!getContentType().equals(
                OperationSetBasicInstantMessaging.HTML_MIME_TYPE))
        {
            output = Html.escapeHtml(output);
        }

        // Process replacements
        output = processReplacements(output);

        // Apply the "edited at" tag for corrected message
        if(correctedMessageUID != null)
        {
            String editedStr = JitsiApplication.getResString(
                    R.string.service_gui_EDITED_AT,
                    GuiUtils.formatTime(getDate()));

            output = "<i>" + output
                         + "  <font color=\"#989898\" >("
                         + editedStr + ")</font></i>";
        }

        cachedOutput = output;

        return cachedOutput;
    }

    /**
     * Processes message content replacement(for smileys).
     * @param content the content to be processed.
     * @return message content with applied replacements.
     */
    private String processReplacements(String content)
    {
        ConfigurationService cfg
                = AndroidGUIActivator.getConfigurationService();

        if(!cfg.getBoolean(
                ReplacementProperty.getPropertyName("SMILEY"), true))
            return content;

        //boolean isEnabled
        //= cfg.getBoolean(ReplacementProperty.REPLACEMENT_ENABLE, true);

        for (ReplacementService source
                : AndroidGUIActivator.getReplacementSources())
        {
            boolean isSmiley = source instanceof SmiliesReplacementService;

            if(!isSmiley)
                continue;

            String sourcePattern = source.getPattern();
            Pattern p = Pattern.compile(
                    sourcePattern,
                    Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = p.matcher(content);

            StringBuilder msgBuff = new StringBuilder();
            int startPos = 0;

            while (m.find())
            {
                msgBuff.append(content.substring(startPos, m.start()));
                startPos = m.end();

                String group = m.group();
                String temp = source.getReplacement(group);
                String group0 = m.group(0);

                if(!temp.equals(group0))
                {
                    msgBuff.append("<IMG SRC=\"");
                    msgBuff.append(temp);
                    msgBuff.append("\" BORDER=\"0\" ALT=\"");
                    msgBuff.append(group0);
                    msgBuff.append("\"></IMG>");
                }
                else
                {
                    msgBuff.append(group);
                }
            }

            msgBuff.append(content.substring(startPos));

            /*
             * replace the content variable with the current replaced
             * message before next iteration
             */
            String msgBuffString = msgBuff.toString();

            if (!msgBuffString.equals(content))
                content = msgBuffString;
        }

        return content;
    }

    /**
     * Returns the content type (e.g. "text", "text/html", etc.).
     * 
     * @return the content type
     */
    @Override
    public String getContentType()
    {
        return contentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatMessage mergeMessage(ChatMessage consecutiveMessage)
    {
        if(messageUID != null &&
                messageUID.equals(consecutiveMessage.getCorrectedMessageUID()))
        {
            return consecutiveMessage;
        }
        return new MergedMessage(this).mergeMessage(consecutiveMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUidForCorrection()
    {
        return messageUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContentForCorrection()
    {
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContentForClipboard()
    {
        return message;
    }

    /**
     * Returns the UID of this message.
     * 
     * @return the UID of this message.
     */
    public String getMessageUID()
    {
        return messageUID;
    }

    /**
     * Returns the UID of the message that this message replaces, or
     * <tt>null</tt> if this is a new message.
     * 
     * @return the UID of the message that this message replaces, or
     * <tt>null</tt> if this is a new message.
     */
    public String getCorrectedMessageUID()
    {
        return correctedMessageUID;
    }

    /**
     * Indicates if given <tt>nextMsg</tt> is a consecutive message.
     *
     * @param nextMsg the next message to check
     * @return <tt>true</tt> if the given message is a consecutive message,
     * <tt>false</tt> - otherwise
     */
    public boolean isConsecutiveMessage(ChatMessage nextMsg)
    {
        boolean uidEqual = messageUID != null
                && messageUID.equals(nextMsg.getCorrectedMessageUID());

        return uidEqual
            || contactName != null
            && (messageType == nextMsg.getMessageType())
            && contactName.equals(nextMsg.getContactName())
            // And if the new message is within a minute from the last one.
            && ((nextMsg.getDate().getTime() - getDate().getTime()) < 60000);

    }

    /**
     * Returns the message type corresponding to the given
     * <tt>MessageReceivedEvent</tt>.
     *
     * @param evt the <tt>MessageReceivedEvent</tt>, that gives us information
     * of the message type
     * @return the message type corresponding to the given
     * <tt>MessageReceivedEvent</tt>
     */
    public static int getMessageType(MessageReceivedEvent evt)
    {
        int eventType = evt.getEventType();

        // Distinguish the message type, depending on the type of event that
        // we have received.
        int messageType = -1;

        if(eventType == MessageReceivedEvent.CONVERSATION_MESSAGE_RECEIVED)
        {
            messageType = INCOMING_MESSAGE;
        }
        else if(eventType == MessageReceivedEvent.SYSTEM_MESSAGE_RECEIVED)
        {
            messageType = SYSTEM_MESSAGE;
        }
        else if(eventType == MessageReceivedEvent.SMS_MESSAGE_RECEIVED)
        {
            messageType = SMS_MESSAGE;
        }

        return messageType;
    }

    static public ChatMessageImpl getMsgForEvent(MessageDeliveredEvent evt)
    {
            final Contact contact = evt.getDestinationContact();
            final Message msg = evt.getSourceMessage();

        logger.info("mychange ChatMessageImpl delivered message is " +msg.getContent() +" to "+evt.getDestinationContact().getAddress());

            return new ChatMessageImpl(
                    contact.getProtocolProvider()
                                .getAccountID().getAccountAddress(),
                    getAccountDisplayName(contact.getProtocolProvider()),
                    evt.getTimestamp(),
                    ChatMessage.OUTGOING_MESSAGE,
                    null,
                    msg.getContent(),
                    msg.getContentType(),
                    msg.getMessageUID(),
                    evt.getCorrectedMessageUID());
    }

     public static ChatMessageImpl getMsgForEvent(final MessageReceivedEvent evt)
    {
        final Contact protocolContact = evt.getSourceContact();
        final Message message = evt.getSourceMessage();
        final MetaContact metaContact
                = AndroidGUIActivator.getContactListService()
                        .findMetaContactByContact(protocolContact);

        logger.info("mychange ChatMessageImpl received message is " +message.getContent() +" from "+evt.getSourceContact().getAddress());

            if (message.getContent().equals("guest")) {
                //show call button
                showcallbuttons();
            }
            else if (message.getContent().equals("motion")) {

            }else if (message.getContent().equals("ack")) {

            }
            else {
                //mychange here after getting the string of image now we have to save the image
                saveimage(message.getContent());
            }

        /*ChatSession  chatController = new ChatSession(metaContact);
        chatController.sendMessage(message.getContent());*/

        return new ChatMessageImpl(
                protocolContact.getAddress(),
                metaContact.getDisplayName(),
                evt.getTimestamp(),
                getMessageType(evt),
                null,
                message.getContent(),
                message.getContentType(),
                message.getMessageUID(),
                evt.getCorrectedMessageUID());
    }

    private static void showcallbuttons() {

        final Activity ctx2 = JitsiApplication.getCurrentActivity();
        final LinearLayout calllayout =(LinearLayout) ctx2.findViewById(R.id.calllayout);
        final LinearLayout checklayout =(LinearLayout) ctx2.findViewById(R.id.checklayout);
        final ImageView imageView = (ImageView) ctx2.findViewById(R.id.imageView2);
        final ProgressBar progressBar1 =(ProgressBar) ctx2.findViewById(R.id.progressBar);
        ctx2.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                   calllayout.setVisibility(View.VISIBLE);
                   checklayout.setVisibility(View.INVISIBLE);
                   imageView.setImageResource(R.drawable.user);
                   progressBar1.setVisibility(ctx2.getCurrentFocus().VISIBLE);
                }catch (Exception e){
                    logger.info("mychange trying to save piture and got error "+e.getMessage());
                }
            }
        });

    }


    //mychange method to convert base64string to image and save as pic.png
    public static void saveimage(String decodeimage) {
        final File file;
        String imageDataString = null;
        try {
            File imagefile = new File(
                    Environment.getExternalStorageDirectory(),
                    "test.png");
            //* Reading a Image file from file system
            //   FileInputStream imageInFile = new FileInputStream(imagefile);
            //   byte imageData[] = new byte[(int) imagefile.length()];
            //   imageInFile.read(imageData);
            //* Converting Image byte array into Base64 String
            // imageDataString = encodeImage(imageData);
            //* Converting a Base64 String into Image byte array
            String newdecode=decodeimage.replace(";","");
            logger.info("saveimage received message is "+newdecode );
            byte[] imageByteArray = decodeImage(decodeimage);
            //* Write a image byte array into file system


                file = new File(Environment.getExternalStorageDirectory(),
                        "pic.jpg");
                logger.info("file exist " + file + ",Bitmap= " + "pic");

            FileOutputStream imageOutFile = new FileOutputStream(file);
            imageOutFile.write(imageByteArray);
            //imageInFile.close();
            imageOutFile.close();

            //todo update image here

            logger.info("mychange trying to take pictur1e");
            final Activity ctx1 = JitsiApplication.getCurrentActivity();
            final Context context= ctx1.getApplicationContext();
            final ImageView imageView = (ImageView) ctx1.findViewById(R.id.imageView2);
            final ImageButton saveimagebutton =(ImageButton) ctx1.findViewById(R.id.savebtn);
            final ImageButton idlemodebutton = (ImageButton) ctx1.findViewById(R.id.idlemodebtn);
            final ProgressBar progressBar = (ProgressBar) ctx1.findViewById(R.id.progressBar);
            ctx1.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Toast.makeText(context,"reload image",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(ctx1.getCurrentFocus().GONE);
                        logger.info("mychange trying to take picture2");
                        imageView.setImageURI(null);
                        imageView.setImageURI(Uri.fromFile(file));
                        saveimagebutton.setImageResource(R.drawable.save);
                        idlemodebutton.setImageResource(R.drawable.home);
                        logger.info("mychange trying to take picture3");
                    }catch (Exception e){
                        logger.info("mychange trying to save piture and got error "+e.getMessage());
                    }


                }
            });





           /* try {
                Activity activity = JitsiApplication.getCurrentActivity();
                ImageView imageView = (ImageView) activity.findViewById(R.id.imageView2);
                imageView.setImageURI(Uri.fromFile(file));
            }
            catch (Exception e){
                logger.info("mychange update image execption "+e.getMessage());
            }*/




            logger.info("Image is Successfully Manipulated!");
        } catch (FileNotFoundException e) {
            logger.info("Image is not found" + e);
        } catch (IOException ioe) {
            logger.info("Image is Exception while reading the Image " + ioe);
        }
    }




    //mychange
    //
    //* Encodes the byte array into base64 string
    //* @param imageByteArray - byte array
    //* @return String a {@link java.lang.String}
    public String encodeImage(byte[] imageByteArray){
        return android.util.Base64.encodeToString(imageByteArray, android.util.Base64.DEFAULT);
    }
    //* Decodes the base64 string into byte array
    //* @param imageDataString - a {@link java.lang.String}
    //* @return byte array

    public static byte[] decodeImage(String imageDataString) {
        return android.util.Base64.decode(imageDataString, Base64.DEFAULT);
    }

    /**
     * Returns the account user display name for the given protocol provider.
     * @param protocolProvider the protocol provider corresponding to the
     * account to add
     * @return The account user display name for the given protocol provider.
     */
    public static String getAccountDisplayName(
            ProtocolProviderService protocolProvider)
    {
        final OperationSetServerStoredAccountInfo accountInfoOpSet
                = protocolProvider.getOperationSet(
                OperationSetServerStoredAccountInfo.class);
        try
        {
            if (accountInfoOpSet != null)
            {
                String displayName
                        = AccountInfoUtils.getDisplayName(accountInfoOpSet);
                if(displayName != null && displayName.length() > 0)
                    return displayName;
            }
        }
        catch(Exception e)
        {
            logger.error("Cannot obtain display name through OPSet");
        }
        return protocolProvider.getAccountID().getDisplayName();
    }
}
