package com.wonders.xlab.cardbag.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hua on 16/8/22.
 * {@link Bean } should override equals and hashCode
 */
public abstract class BaseRecyclerViewAdapter<Bean, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<Bean> mBeanList;

    private OnClickListener mOnClickListener;

    private OnLongClickListener mOnLongClickListener;

    /**
     * @param holder
     * @param position
     * @return true: won't call the {@link #mOnClickListener}
     */
    protected abstract boolean onItemClick(VH holder, int position);

    /**
     * @param holder
     * @param position
     * @return true: won't call the {@link #mOnLongClickListener}
     */
    protected abstract boolean onItemLongClick(VH holder, int position);

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public interface OnLongClickListener {
        void onItemLongClick(int position);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClick(holder, holder.getAdapterPosition())) {
                    return true;
                } else {
                    if (null != mOnLongClickListener) {
                        mOnLongClickListener.onItemLongClick(holder.getAdapterPosition());
                    }
                    return false;
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnClickListener && !onItemClick(holder, holder.getAdapterPosition())) {
                    mOnClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    public void insertToFist(Bean bean) {
        if (bean == null) {
            return;
        }
        if (mBeanList == null) {
            mBeanList = new ArrayList<>();
        }
        mBeanList.add(0, bean);
        notifyItemInserted(0);
    }

    public void appendToLast(Bean bean) {
        if (bean == null) {
            return;
        }
        if (mBeanList == null) {
            mBeanList = new ArrayList<>();
        }
        mBeanList.add(bean);
        notifyItemInserted(mBeanList.size());
    }

    public void remove(int position) {
        if (mBeanList != null && mBeanList.size() > position) {
            mBeanList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * {@link Bean } should override equals and hashCode
     *
     * @param bean
     */
    public void remove(Bean bean) {
        if (mBeanList != null) {
            int i = mBeanList.indexOf(bean);
            if (i >= 0) {
                mBeanList.remove(bean);
                notifyItemRemoved(i);
            }
        }
    }

    public void clear() {
        if (mBeanList != null) {
            int size = mBeanList.size();
            mBeanList.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    /**
     * 因为这里需要有移动的动画效果，所以采用了遍历的方式来交换位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void swap(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(this.mBeanList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(this.mBeanList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setDatas(List<Bean> mBeanList) {
        if (this.mBeanList == null) {
            this.mBeanList = new ArrayList<>();
        } else {
            this.mBeanList.clear();
        }
        if (mBeanList == null) {
            return;
        }
        this.mBeanList.addAll(mBeanList);
        notifyDataSetChanged();
    }

    public void appendDatas(List<Bean> mBeanList) {
        if (mBeanList == null) {
            return;
        }
        if (this.mBeanList == null) {
            this.mBeanList = new ArrayList<>();
        }
        int size = this.mBeanList.size();
        this.mBeanList.addAll(mBeanList);
        notifyItemRangeInserted(size, mBeanList.size());
    }

    public List<Bean> getBeanList() {
        if (mBeanList == null) {
            mBeanList = new ArrayList<>();
        }
        return mBeanList;
    }

    public Bean getBean(int position) {
        if (mBeanList != null && mBeanList.size() > position) {
            return mBeanList.get(position);
        }
        return null;

    }
}
