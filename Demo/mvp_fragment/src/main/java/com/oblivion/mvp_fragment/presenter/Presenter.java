package com.oblivion.mvp_fragment.presenter;

import com.oblivion.mvp_fragment.model.IModelGetData;
import com.oblivion.mvp_fragment.model.impl.IModelGetDataImpl;
import com.oblivion.mvp_fragment.view.IShowView;

/**
 * Created by oblivion on 2016/11/18.
 */
public class Presenter {
    private IModelGetData modelGetData;
    private IShowView showView;

    public Presenter(IShowView showView) {
        this.showView = showView;
        modelGetData = new IModelGetDataImpl();
    }

    public void doLogic() {
        String data = modelGetData.getData();
        if (data != null && data.length() > 0) {
            showView.showSuccess(1000);
        }
    }
}
