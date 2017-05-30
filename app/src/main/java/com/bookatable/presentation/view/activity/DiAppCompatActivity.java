package com.bookatable.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.bookatable.presentation.BookATableApplication;
import com.bookatable.presentation.di.DiActivityModule;

public class DiAppCompatActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    BookATableApplication.getScopedGraph(new DiActivityModule()).inject(this);
    super.onCreate(savedInstanceState);
  }

  @Override public void onContentChanged() {
    super.onContentChanged();
    ButterKnife.bind(this);
  }
}
