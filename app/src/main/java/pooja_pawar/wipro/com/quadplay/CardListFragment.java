package pooja_pawar.wipro.com.quadplay;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class CardListFragment extends DialogFragment{

    private long mobile;
    private DbHandlersUsers db;
    private SessionManager sessionManager;
    private RecyclerView rv;
    private CardBeanAdapter adapter;
    private ArrayList<CardBean> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.existing_card_list, container, false);
        // Recycler
        rv = (RecyclerView)viewGroup.findViewById(R.id.recycleList);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadDatabase();
        return viewGroup;
    }

    public void loadDatabase() {
        sessionManager = new SessionManager(getActivity());
        mobile = sessionManager.getMobile();
        db = new DbHandlersUsers(getActivity());
        dataList = db.getUserCards(mobile);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new CardBeanAdapter(getActivity(),dataList);
        adapter.setOnTapList(new OnTapList() {
            @Override
            public void OnTapView(long cno) {
                Toast.makeText(getContext(), "You Selected " + cno, Toast.LENGTH_SHORT).show();
                OnTapList ac = (OnTapList)getContext();
                ac.OnTapView(cno);
                dismiss();
            }
        });
        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setAdapter(adapter);

    }

}
