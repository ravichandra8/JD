/*
 * Copyright (c) 2017 Selva.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.user.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.SubChildCatData;
import com.example.user.myapplication.model.childCatData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class SecondLevelAdapter extends BaseExpandableListAdapter {
    private Context context;


    ArrayList<List<String>> data;

    List<childCatData> headers;

    ImageView ivGroupIndicator;


    public SecondLevelAdapter(Context context, List<childCatData> headers, ArrayList<List<String>> data) {
        this.context = context;
        this.data = data;
        this.headers = headers;

    }

    @Override
    public Object getGroup(int groupPosition) {

        return headers.get(groupPosition).getNavNamechild();
    }

    @Override
    public int getGroupCount() {

        return headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_second, null);
        TextView text = (TextView) convertView.findViewById(R.id.rowSecondText);
        String groupText = getGroup(groupPosition).toString();
        text.setText(groupText);
        ImageView iv=(ImageView)convertView.findViewById(R.id.ivGroupIndicator);
        if (isExpanded) {
            iv.setImageResource(R.drawable.arrow_white_down);
        } else {
            iv.setImageResource(R.drawable.arrow_white_right);
        }

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {


        List<String> childData = data.get(groupPosition);


        return childData.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_third, null);

        TextView textView = (TextView) convertView.findViewById(R.id.rowThirdText);

        List<String> childArray = data.get(groupPosition);

        String text = childArray.get(childPosition).toString();

        textView.setText(text);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<String> children = data.get(groupPosition);


        return children.size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
