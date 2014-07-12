/*
 * Copyright 2013 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haarman.listviewanimations.itemmanipulation;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.haarman.listviewanimations.MyListActivity;
import com.haarman.listviewanimations.R;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.rewrite.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.rewrite.TouchViewDraggableManager;
import com.nhaarman.listviewanimations.swinginadapters.simple.AlphaInAnimationAdapter;

public class DragAndDropActivity extends MyListActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draganddrop);

        final DynamicListView listView = (DynamicListView) findViewById(R.id.activity_draganddrop_listview);
        listView.setDivider(null);

        final ArrayAdapter<String> adapter = createListAdapter();
        AlphaInAnimationAdapter animAdapter = new AlphaInAnimationAdapter(adapter);
        animAdapter.setAbsListView(listView);

        assert animAdapter.getViewAnimator() != null;
        animAdapter.getViewAnimator().setInitialDelayMillis(300);

        listView.setAdapter(animAdapter);
        listView.setOnItemLongClickListener(new MyOnItemLongClickListener(listView));
        listView.setDraggableManager(new TouchViewDraggableManager(R.id.touchview));

//        listView.setOnItemMovedListener(new MyOnItemMovedListener(adapter));

        Toast.makeText(this, getString(R.string.long_press_to_drag), Toast.LENGTH_LONG).show();
    }

    private static class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        private final DynamicListView mListView;

        public MyOnItemLongClickListener(final DynamicListView listView) {
            mListView = listView;
        }

        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            mListView.startDragging(position);
            return true;
        }
    }

    private class MyOnItemMovedListener implements OnItemMovedListener {
        private final ArrayAdapter<String> mAdapter;

        MyOnItemMovedListener(final ArrayAdapter<String> adapter) {
            mAdapter = adapter;
        }

        @Override
        public void onItemMoved(final int newPosition) {
            Toast.makeText(getApplicationContext(), getString(R.string.moved, mAdapter.getItem(newPosition), newPosition), Toast.LENGTH_SHORT).show();
        }
    }
}
