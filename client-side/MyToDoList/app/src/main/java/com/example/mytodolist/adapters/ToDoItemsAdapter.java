package com.example.mytodolist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mytodolist.R;
import com.example.mytodolist.TodoDetailActivity;
import com.example.mytodolist.dialogs.TodoItemDetailDialog;
import com.example.mytodolist.models.TodoItemModel;
import com.example.mytodolist.modules.DateManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoItemsAdapter extends RecyclerView.Adapter<ToDoItemsAdapter.ToDoItemViewHolder> {


    private Context mContext;
    private List<TodoItemModel> mTodoItemModels;

    public ToDoItemsAdapter(Context mContext, List<TodoItemModel> todoItemModels) {
        this.mContext = mContext;
        this.mTodoItemModels = todoItemModels;
    }


    public void setState(int position, int state) {
        mTodoItemModels.get(position).setState(state);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mTodoItemModels.remove(position);
        notifyDataSetChanged();
    }

    public void setList(List<TodoItemModel> todoItemModels) {
        mTodoItemModels = todoItemModels;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ToDoItemsAdapter.ToDoItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item_a_todo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ToDoItemViewHolder holder, final int i) {
        final TodoItemModel todoItemModel = mTodoItemModels.get(i);

        if (todoItemModel != null) {
            holder.txtName.setText(todoItemModel.getName());
            holder.txtDeadline.setText(DateManager.getFromattedTime(todoItemModel.getDeadline()));
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TodoItemDetailDialog(mContext, todoItemModel).show();

                }
            });

            if (todoItemModel.getState() == 0) {
                holder.txtStatus.setText("Expired");
            }else if(todoItemModel.getState() == 1){
                holder.txtStatus.setText("Not Completed");
            }else{
                holder.txtStatus.setText("Completed");
            }


            holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(mContext, holder.btnMenu);
                    //Inflating the Popup using xml file
                    if (todoItemModel.getState() == 0 || todoItemModel.getState() == 2) {
                        popup.getMenuInflater().inflate(R.menu.menu_only_remove, popup.getMenu());
                    } else if (todoItemModel.getState() == 1) {
                        popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                    }


                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.item1:
                                    ((TodoDetailActivity) mContext).removeDialog(todoItemModel.getId(), i);
                                    return true;
                                case R.id.item2:
                                    ((TodoDetailActivity) mContext).markAsCompleted(todoItemModel.getId(), i);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();//showing popup menu
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return mTodoItemModels.size();
    }


    static final class ToDoItemViewHolder extends RecyclerView.ViewHolder {

        private View container;

        @BindView(R.id.txtName)
        TextView txtName;

        @BindView(R.id.txtDeadline)
        TextView txtDeadline;

        @BindView(R.id.btnMenu)
        RelativeLayout btnMenu;

        @BindView(R.id.txtStatus)
        TextView txtStatus;

        @BindView(R.id.layoutCard)
        RelativeLayout layoutCard;


        public ToDoItemViewHolder(View itemView) {
            super(itemView);
            this.container = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
