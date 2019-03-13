package com.example.mytodolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mytodolist.MainActivity;
import com.example.mytodolist.R;
import com.example.mytodolist.TodoDetailActivity;
import com.example.mytodolist.models.TodoModel;
import com.example.mytodolist.modules.DateManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {


    private Context mContext;
    private List<TodoModel> mTodoLists;

    public TodoListAdapter(Context mContext, List<TodoModel> todoLists) {
        this.mContext = mContext;
        this.mTodoLists = todoLists;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TodoListAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item_todo_list, viewGroup, false));
    }


    public void removeItem(int position){
        mTodoLists.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  final int i) {
        final TodoModel todoModel = mTodoLists.get(i);
        if(todoModel != null){
            holder.txtName.setText(todoModel.getName());
            holder.txtCreatedAt.setText(DateManager.getFromattedTime(todoModel.getCreatedAt()));
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TodoDetailActivity.class);
                    intent.putExtra(TodoDetailActivity.TODO_ID,String.valueOf(todoModel.getId()));
                    intent.putExtra(TodoDetailActivity.TODO_NAME,String.valueOf(todoModel.getName()));
                    mContext.startActivity(intent);
                }
            });

            holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) mContext).removeDialog(todoModel.getId(),i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTodoLists.size();
    }

    static final class  ViewHolder extends RecyclerView.ViewHolder{

        private View container;

        @BindView(R.id.txtName)
        TextView txtName;

        @BindView(R.id.txtCreatedAt)
        TextView txtCreatedAt;

        @BindView(R.id.btnRemove)
        RelativeLayout btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            this.container =itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
