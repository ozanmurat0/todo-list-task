package com.example.mytodolist.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.mytodolist.R;
import com.example.mytodolist.models.TodoItemModel;
import com.example.mytodolist.modules.DateManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoItemDetailDialog extends Dialog {

    private TodoItemModel mTodoItem;

    @BindView(R.id.txtDetailName)
    TextView txtDetailName;

    @BindView(R.id.txtDetailDeadLine)
    TextView txtDetailDeadLine;

    @BindView(R.id.txtDetailDescription)
    TextView txtDetailDescription;

    @BindView(R.id.txtDetailStatus)
    TextView txtDetailStatus;

    public TodoItemDetailDialog(@NonNull Context context, TodoItemModel todoItem) {
        super(context);
        this.mTodoItem = todoItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_todo_item_detail);
        ButterKnife.bind(this);
        setTodoDetails();
    }


    private void setTodoDetails(){
        txtDetailName.setText(mTodoItem.getName());
        txtDetailDescription.setText(mTodoItem.getDescription());
        txtDetailDeadLine.setText(DateManager.getFromattedTime(mTodoItem.getDeadline()));
        String state = "";

        if(mTodoItem.getState() == 0){
            state = "Expired";
        }else if(mTodoItem.getState() == 1){
            state = "Not Completed";
        }else{
            state = "Completed";
        }

        txtDetailStatus.setText(state);
    }
}
