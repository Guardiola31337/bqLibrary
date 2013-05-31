package com.bqlibrary.guardiola.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

public abstract class ActBase extends Activity {

	/**
	 * Construct of this class
	 * @param savedInstanceState - data
	 */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //associate XML
        setContentView(this.getXMLToInflate());
        
        //recogemos los datos
        Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.dataReceived(extras);
		}
		
        //inicializamos las VIEWS
        initViews();
        
        //inicializamos los valores / textos
        initValues();
        
        //inicializamos los listeners
        initListeners();
        
        //inicializacion si fuera necesaria de otro tipo
        init();
    }
    
    /**
	 * Must return the activity to inflate, the XML given by the R.layout.
	 * @return R.layout. associated to this layout
	 */
	protected abstract int getXMLToInflate();
    
    /**
	 * Initialize views of layout: findViewById(R.id. )
	 */
	protected abstract void dataReceived(Bundle extras);
	
	/**
	 * Must save the data required to be received when activity is resumed
	 * extras.putString(...)
	 * @param extras
	 */
	protected abstract void dataToSave(Bundle extras);
    
    /**
	 * Initialize views of layout: findViewById(R.id. )
	 */
	protected abstract void initViews();
	
	/**
	 * Initialize values of fields, texts, data... v.setText(R.string. )
	 */
	protected abstract void initValues();
	
	/**
	 * Initialize all the listeners need for the values (touches, gps, etc... )
	 */
	protected abstract void initListeners();
	
	/**
	 * Initialize everything you need after the layout has been created
	 */
	protected abstract void init();
	
	/**
	 * Receive the events from buttons created in XML assigned as this name
	 * @param view - View clicked
	 */
	public abstract void click(View view);
	
	/**
     * Handler to receive messages from other classes
     */
	protected Handler handler = new Handler(){	
		
		@Override
		public void handleMessage(Message msg) {
			
			//send this message as received
			handleMessageReceived(msg.what, msg.arg1, msg.arg2, msg.obj);
			
		}
    };
    
    /**
     * Receive the messages sent to main handler of LayBase
     * @param what - what data from message
     * @param arg1 - value of arg1 from message
     * @param arg2 - value of arg2 from message
     * @param obj - object received of message
     */
    protected abstract void handleMessageReceived(int what, int arg1, int arg2, Object obj);
	
    /**
     * Send a message to this activity, calling to handleMessageReceived
     * @param msg Message to send to the handler
     */
    public void sendMessage(Message msg){
    	
    	//send this message as received
		handleMessageReceived(msg.what, msg.arg1, msg.arg2, msg.obj);
    	
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    }
    
    
    @Override
    public void onPause(){
    	super.onPause();
    }
    
    /**
     * Save the instance of the activity
     */
    @Override
    protected void onSaveInstanceState(Bundle bundle){
    	this.dataToSave(bundle);
    }
    
    /**
     * Restore de instance of the activity, calling getBundleData, initValues, initListeners and init
     */
    @Override
    protected void onRestoreInstanceState(Bundle bundle){
    	
    	this.dataReceived(bundle);
		this.initValues();
		this.initListeners();
		this.init();
    	
    }
    
    @Override
	protected abstract void onActivityResult(int requestCode, int resultCode, Intent data);
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if(this.keyPressed(keyCode, event)){
    		return true;
    	}else{
    		return super.onKeyDown(keyCode, event);
    	}
    }
    
    /**
     * Send a key event and receive if the event has been consumed or not
     * @param keyCode - code of the key
     * @param event - event of the key (up, down, ...)
     * @return TRUE if event consumed, FALSE otherwise
     */
    protected abstract boolean keyPressed(int keyCode, KeyEvent event);
    
    /**
     * Show a loding message
     */
    public abstract void showLoading();
    
    /**
     * Show a loding message
     */
    public abstract void hideLoading();
    
}
