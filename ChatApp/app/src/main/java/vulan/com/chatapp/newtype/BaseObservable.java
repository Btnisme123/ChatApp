package vulan.com.chatapp.newtype;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;


/**
 * Created by vulan on 13/01/2017.
 */

public class BaseObservable implements Observable {

    private transient PropertyChangeRegistry mCallBack;

    public  BaseObservable(){

    }
    public BaseObservable(PropertyChangeRegistry callBack) {
        mCallBack = callBack;
    }


    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        if (mCallBack == null) {
            mCallBack = new PropertyChangeRegistry();
        }
        mCallBack.add(onPropertyChangedCallback);
    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        if (mCallBack!= null) {
            mCallBack.remove(onPropertyChangedCallback);
        }
    }
    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public synchronized void notifyChanged(){
        if(mCallBack!=null){
            mCallBack.notifyCallbacks(this,0,null);
        }
    }

    public void notifyPropertiyChanged(int fieldID){
        if(mCallBack!=null){
            mCallBack.notifyCallbacks(this,fieldID,null);
        }
    }
}
