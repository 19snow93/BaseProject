package com.cpgc.baseproject.base.recyclerview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by leo on 2017/5/8.
 */

public abstract class BaseAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K> {

    public static final int HEADER_VIEW = 0x00000111;
    public static final int LOADING_VIEW = 0x00000222;
    public static final int FOOTER_VIEW = 0x00000333;
    public static final int EMPTY_VIEW = 0x00000555;

    protected Context mContext;
    protected List<T> mDatas;
    protected int mLayoutResId;
    //header footer
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    //empty
    private FrameLayout mEmptyLayout;

    private boolean mHeadAndEmptyEnable;
    private boolean mFootAndEmptyEnable;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private RecyclerView mRecyclerView;

    public BaseAdapter(@LayoutRes int layoutResId, Context context, List<T> datas){
        if(layoutResId != 0){
            this.mLayoutResId = layoutResId;
        }
        mContext = context;
        mDatas = datas;
    }

    public BaseAdapter(Context context, List<T> datas){
        this(0,context,datas);
    }

    public BaseAdapter(int layoutResId,Context context){
        this(layoutResId,context,null);
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    private void checkNotNull() {
        if (getRecyclerView() == null) {
            throw new RuntimeException("please bind recyclerView first!");
        }
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        K baseViewHolder = null;
        switch (viewType) {
            case HEADER_VIEW:
                baseViewHolder = (K) BaseViewHolder.createViewHolder(mContext,mHeaderLayout);
                break;
            case EMPTY_VIEW:
                baseViewHolder = (K) BaseViewHolder.createViewHolder(mContext,mEmptyLayout);
                break;
            case FOOTER_VIEW:
                baseViewHolder = (K) BaseViewHolder.createViewHolder(mContext,mFooterLayout);
                break;
            default:
                baseViewHolder = (K) BaseViewHolder.createViewHolder(mContext,parent,mLayoutResId);
                bindViewClickListener(baseViewHolder);
        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        int viewType = holder.getItemViewType();

        switch (viewType) {
            case 0:
                convert(holder,position, mDatas.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                break;
            case HEADER_VIEW:
                break;
            case EMPTY_VIEW:
                break;
            case FOOTER_VIEW:
                break;
            default:
                convert(holder,position, mDatas.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        int itemCount;
        if(getEmptyViewCount() == 1){
            itemCount = 1;
            if(mHeadAndEmptyEnable && getHeaderLayoutCount() != 0){
                itemCount++;
            }
            if(mFootAndEmptyEnable && getFooterLayoutCount() != 0){
                itemCount++;
            }
        }else {
            itemCount = getHeaderLayoutCount() + mDatas.size() + getFooterLayoutCount();
        }
        return itemCount;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    private void bindViewClickListener(final BaseViewHolder baseViewHolder){
        if(baseViewHolder == null){
            return;
        }

        View view = baseViewHolder.itemView;
        if(view != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getOnItemClickListener() != null){
                        mOnItemClickListener.onItemClick(v,baseViewHolder.getLayoutPosition());
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(getOnItemLongClickListener() != null){
                        mOnItemLongClickListener.onItemLongClick(v,baseViewHolder.getLayoutPosition());
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getEmptyViewCount() == 1) {
            boolean header = mHeadAndEmptyEnable && getHeaderLayoutCount() != 0;
            switch (position) {
                case 0:
                    if (header) {
                        return HEADER_VIEW;
                    } else {
                        return EMPTY_VIEW;
                    }
                case 1:
                    if (header) {
                        return EMPTY_VIEW;
                    } else {
                        return FOOTER_VIEW;
                    }
                case 2:
                    return FOOTER_VIEW;
                default:
                    return EMPTY_VIEW;
            }
        }
        int numHeaders = getHeaderLayoutCount();
        if (position == 0 && numHeaders == 1) {
            return HEADER_VIEW;
        }else if(position == mDatas.size() + 1 && getFooterLayoutCount() == 1){
            return FOOTER_VIEW;
        }
        return 0;
    }

    public void setEmptyView(View emptyView) {
        boolean insert = false;
        if (mEmptyLayout == null) {
            mEmptyLayout = new FrameLayout(emptyView.getContext());
            final RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
            final ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
            if (lp != null) {
                layoutParams.width = lp.width;
                layoutParams.height = lp.height;
            }
            mEmptyLayout.setLayoutParams(layoutParams);
            insert = true;
        }
        mEmptyLayout.removeAllViews();
        mEmptyLayout.addView(emptyView);
        if (insert) {
            if (getEmptyViewCount() == 1) {
                int position = 0;
                if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                    position++;
                }
                notifyItemInserted(position);
            }
        }
    }

    public void setHeaderView(View headerView){
        if (mHeaderLayout != null) {
            mHeaderLayout.removeAllViews();
        }
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(headerView.getContext());
            mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
            mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }
        mHeaderLayout.addView(headerView);
        if (mHeaderLayout.getChildCount() == 1) {
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
    }

    public void setFooterView(View footerView){
        if (mFooterLayout != null) {
            mFooterLayout.removeAllViews();
        }
        if (mFooterLayout == null) {
            mFooterLayout = new LinearLayout(footerView.getContext());
            mFooterLayout.setOrientation(LinearLayout.VERTICAL);
            mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }
        mFooterLayout.addView(footerView);
        if (mFooterLayout.getChildCount() == 1) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
    }

    /**
     * set emptyView show if adapter is empty and want to show headview and footview
     * Call before {@link RecyclerView#setAdapter(RecyclerView.Adapter)}
     * 设置emptyView的同时想显示headerView和footerView
     * @param isHeadAndEmpty
     * @param isFootAndEmpty
     */
    public void setHeaderFooterEmpty(boolean isHeadAndEmpty, boolean isFootAndEmpty) {
        mHeadAndEmptyEnable = isHeadAndEmpty;
        mFootAndEmptyEnable = isFootAndEmpty;
    }

    private int getHeaderViewPosition() {
        //Return to header view notify position
        if (getEmptyViewCount() == 1) {
            if (mHeadAndEmptyEnable) {
                return 0;
            }
        } else {
            return 0;
        }
        return -1;
    }

    /**
     * if show empty view will be return 1 or not will be return 0
     * 如果有显示emptyView将返回1，如果没有就返回0
     * @return
     */
    public int getEmptyViewCount() {
        if (mEmptyLayout == null || mEmptyLayout.getChildCount() == 0) {
            return 0;
        }
        if (mDatas.size() != 0) {
            return 0;
        }
        return 1;
    }

    private int getFooterViewPosition() {
        //Return to footer view notify position
        if (getEmptyViewCount() == 1) {
            int position = 1;
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                position++;
            }
            if (mFootAndEmptyEnable) {
                return position;
            }
        } else {
            return getHeaderLayoutCount() + mDatas.size();
        }
        return -1;
    }

    public int getHeaderLayoutCount(){
        if(mHeaderLayout == null || mHeaderLayout.getChildCount() == 0){
            return 0;
        }
        return 1;
    }

    public int getFooterLayoutCount(){
        if(mFooterLayout == null || mFooterLayout.getChildCount() == 0){
            return 0;
        }
        return 1;
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if (mSpanSizeLookup == null) {
                        return isFixedViewType(type) ? gridManager.getSpanCount() : 1;
                    } else {
                        return (isFixedViewType(type)) ? gridManager.getSpanCount() : mSpanSizeLookup.getSpanSize(gridManager,
                                position - getHeaderLayoutCount());
                    }
                }

            });
        }
    }

    protected boolean isFixedViewType(int type) {
        return type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type ==
                LOADING_VIEW;
    }

    private SpanSizeLookup mSpanSizeLookup;

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    /**
     * @param spanSizeLookup instance to be used to query number of spans occupied by each item
     *                          用于查询每个项目占用的跨度的实例
     */
    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    protected abstract void convert(K viewHolder, int position, T item);
}
