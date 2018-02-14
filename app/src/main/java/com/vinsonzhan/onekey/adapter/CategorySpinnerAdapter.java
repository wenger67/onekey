package com.vinsonzhan.onekey.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.greendao.CategoryDao;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/31/18 3:06 PM
 * author：Vinson.Zhan
 * comment：
 */
public class CategorySpinnerAdapter extends CursorAdapter {

    public CategorySpinnerAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_category_spinner, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String iconStr = cursor.getString(cursor.getColumnIndex(CategoryDao.Properties.Icon
                .columnName));
        String nameStr = cursor.getString(cursor.getColumnIndex(CategoryDao.Properties.Name
                .columnName));
        viewHolder.icon.setBootstrapText(new BootstrapText.Builder(context)
                .addFontAwesomeIcon(iconStr).build());
        viewHolder.name.setText(nameStr);
    }

    private class ViewHolder {
        View root;
        AwesomeTextView icon;
        TextView name;

        ViewHolder(View view) {
            this.root = view;
            this.icon = view.findViewById(R.id.icon);
            this.name = view.findViewById(R.id.name);
        }
    }
}
