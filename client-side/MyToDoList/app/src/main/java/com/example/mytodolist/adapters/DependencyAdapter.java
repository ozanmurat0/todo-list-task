package com.example.mytodolist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mytodolist.AddNewTodoListItemActivity;
import com.example.mytodolist.R;
import com.example.mytodolist.models.TodoItemModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DependencyAdapter extends RecyclerView.Adapter<DependencyAdapter.DependencyViewHolder> {

    private Context mContext;
    private List<TodoItemModel> mTodoItemModels;

    public DependencyAdapter(Context mContext, List<TodoItemModel> todoItemModels) {
        this.mContext = mContext;
        this.mTodoItemModels = todoItemModels;
    }

    @NonNull
    @Override
    public DependencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DependencyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item_dependency,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DependencyViewHolder holder, int i) {
        final TodoItemModel itemModel = mTodoItemModels.get(i);
        if(itemModel != null){
            holder.txtName.setText(itemModel.getName());
            holder.chkDependency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ((AddNewTodoListItemActivity)mContext).addDependencies(itemModel.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return mTodoItemModels.size();
    }

    static final class DependencyViewHolder extends RecyclerView.ViewHolder{
        private View container;

        @BindView(R.id.chkDependency)
        CheckBox chkDependency;

        @BindView(R.id.txtName)
        TextView txtName;


        public DependencyViewHolder(View itemView) {
            super(itemView);
            this.container = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
