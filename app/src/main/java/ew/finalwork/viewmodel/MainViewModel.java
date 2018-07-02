package ew.finalwork.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

public class MainViewModel extends ViewModel  implements NavigationView.OnNavigationItemSelectedListener{

    public LiveData<MenuItem> getItemMutableLiveData() {
        return itemMutableLiveData;
    }

    private MutableLiveData<MenuItem> itemMutableLiveData;

    public MainViewModel(){
        super();
        itemMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        itemMutableLiveData.postValue(item);
        return true;
    }

}
