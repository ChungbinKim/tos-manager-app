package com.example.tosmanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.TableView;
import com.example.tosmanager.R;
import com.example.tosmanager.model.ExtraName;
import com.example.tosmanager.model.ListContent;
import com.example.tosmanager.model.ListContents;
import com.example.tosmanager.model.TableContent;
import com.example.tosmanager.model.TermsSummary;
import com.example.tosmanager.util.CreateDialog;
import com.example.tosmanager.util.Metrics;
import com.example.tosmanager.viewmodel.TosDetailsViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TosDetailsActivity extends AppCompatActivity {
    private TosDetailsViewModel viewModel;
    private boolean deleteConfirmed;

    // UI
    private TextView nameText;
    private ImageView editName;
    private LinearLayout layout;

    private final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    private final String DATETIME_PATTERN = "^\\d+-\\d+-\\d+ \\d+:\\d+:\\d+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tos_details);
        getSupportActionBar().setTitle(R.string.title_tos_details);

        viewModel = new ViewModelProvider(this).get(TosDetailsViewModel.class);

        String name = getIntent().getStringExtra(ExtraName.TERMS_NAME);
        if (name == null) {
            name = DATETIME_FORMAT.format(new Date());
        }
        viewModel.getServiceName().setValue(name);

        // 서비스 이름
        nameText = findViewById(R.id.tosDetailsName);
        viewModel.getServiceName().observe(this, serviceName -> {
            nameText.setText(serviceName);
        });

        // 이름 변경 버튼
        editName = findViewById(R.id.tosDetailsEditName);
        editName.setOnClickListener(v -> {
            final View view = getLayoutInflater().inflate(R.layout.dialog_edit_text, null);
            final EditText editText = view.findViewById(R.id.dialogEditText);

            CharSequence prevName = viewModel.getServiceName().getValue();
            if (!prevName.toString().matches(DATETIME_PATTERN)) {
                editText.setText(prevName);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage(R.string.text_edit_name)
                    .setView(view)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        viewModel.getServiceName().setValue(editText.getText().toString());
                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());

            builder.create().show();
        });

        layout = findViewById(R.id.tosDetailsLayout);
        viewModel.fetchTermsSummary(name).subscribe(this::renderSummary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tos_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        deleteConfirmed = false;
        if (item.getItemId() == R.id.deleteTos) {
            CreateDialog.createPrompt(this, R.string.delete_tos_dialog_message, (dialog, which) -> {
                deleteConfirmed = true;
            }).show();
        }
        return deleteConfirmed;
    }

    private void renderSummary(TermsSummary summary) {
        final ListContents lists = summary.getListContents();
        final CharSequence[] keys = new CharSequence[]{"사업자 권리", "사업자 의무"};

        for (CharSequence k : keys) {
            ListContent content = lists.getContent(k);
            if (content != null) {
                View view = getLayoutInflater().inflate(R.layout.summary_list, null);

                // 리스트 제목
                TextView listTitle = view.findViewById(R.id.summaryListTitle);
                listTitle.setText(content.getKey());

                // 리스트 내용
                TextView listContent = view.findViewById(R.id.summaryListContent);
                listContent.append(toList(content.getItems()));

                layout.addView(view);
            }
        }

        TableView tableView = new TableView(this);
        SummaryTableAdapter adapter = new SummaryTableAdapter();
        tableView.setAdapter(adapter);
        setAllItems(adapter, summary.getTableContent(), viewModel.getServiceName().getValue());
        layout.addView(tableView);
    }

    private void setAllItems(SummaryTableAdapter adapter, TableContent table, CharSequence serviceName) {
        List<CharSequence> colHeaders = new ArrayList<>();
        colHeaders.add(serviceName);

        List<CharSequence> rowHeaders = new ArrayList<>(table.getRowKeys());

        List<List<CharSequence>> cells = new ArrayList<>();
        for (CharSequence rowKey : rowHeaders) {
            List<CharSequence> cell = new ArrayList<>();
            cell.add(table.getRow(rowKey).toCharSequence());
            cells.add(cell);
        }

        adapter.setAllItems(colHeaders, rowHeaders, cells);
    }

    private Spannable toList(Iterable<CharSequence> items) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        for (CharSequence i : items) {
            Spannable spannable = new SpannableString(i);
            spannable.setSpan(new BulletSpan(Metrics.dp(8, this)), 0, i.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(spannable).append('\n');
        }

        return builder;
    }
}