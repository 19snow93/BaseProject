package com.cpgc.baseproject.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import com.cpgc.baseproject.R;
import com.cpgc.baseproject.base.bean.WXItemBean;
import com.cpgc.baseproject.base.recyclerview.BaseAdapter;
import com.cpgc.baseproject.base.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * Created by leo on 2017/5/31.
 */

public class WeChatAdapter extends BaseAdapter<WXItemBean,BaseViewHolder> {

    public WeChatAdapter(@LayoutRes int layoutResId, Context context, List datas) {
        super(layoutResId, context, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, int position, WXItemBean item) {
        viewHolder.setText(R.id.tv_title,item.getTitle());
        viewHolder.setText(R.id.tv_description,item.getDescription());
    }

}
