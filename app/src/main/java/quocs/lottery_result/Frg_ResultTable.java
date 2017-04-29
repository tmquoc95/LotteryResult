package quocs.lottery_result;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frg_ResultTable.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frg_ResultTable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frg_ResultTable extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;
    private LotteryDataPerRegion lotteryDataPerRegion;
    private RecyclerView recyclerViewPrizes;

    public Frg_ResultTable() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Frg_ResultTable newInstance() {
        Frg_ResultTable fragment = new Frg_ResultTable();
        return fragment;
    }

    public void updateView (LotteryDataPerRegion lotteryDataPerRegion) {
        this.lotteryDataPerRegion = lotteryDataPerRegion;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_table, container, false);
        M00N_ListAdapter m00N_listAdapter = new M00N_ListAdapter(getContext(),
                android.R.layout.simple_list_item_1, this.lotteryDataPerRegion.lstValues);
//        ArrayAdapter adapter = new ArrayAdapter(getContext(),  android.R.layout.simple_list_item_1, this.lotteryDataPerRegion.lstValues);

        Spinner spinnerDate = (Spinner) view.findViewById(R.id.spinnerDate);
        spinnerDate.setAdapter(m00N_listAdapter);

        recyclerViewPrizes = (RecyclerView) view.findViewById(R.id.recyclerViewPrizes);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPrizes.setLayoutManager( linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewPrizes.getContext(),
                linearLayoutManager.getOrientation());

        recyclerViewPrizes.addItemDecoration(dividerItemDecoration);


        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RowRecAdapter rowRecAdapter = new RowRecAdapter(getContext(), lotteryDataPerRegion.lstChildren[position]);
                recyclerViewPrizes.setAdapter(rowRecAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}


class RowRecAdapter extends RecyclerView.Adapter<RowRecAdapter.PrizeRowViewHolder> {

    private Context mContext;
    private  LotteryDataPerDay lotteryDataPerDay;
    private LayoutInflater mLayoutInflater;

    public RowRecAdapter(Context context, LotteryDataPerDay lotteryDataPerDay) {
        mContext = context;
        this.lotteryDataPerDay = lotteryDataPerDay;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PrizeRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.lottery_result_row, parent, false);
        return new PrizeRowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (PrizeRowViewHolder viewholder, int position) {
        LotteryDataPerPrize perPrize = lotteryDataPerDay.lstChildren[position];
        StringBuilder sb = new StringBuilder();
        for (String value: perPrize.lstValues) {
            sb.append(value).append('\n');
        }

        viewholder.tvPrizeName.setText(perPrize.prizeName);
        viewholder.tvLstResult.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return lotteryDataPerDay.lstChildren.length;
    }

    class PrizeRowViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrizeName;
        TextView tvLstResult;

        public PrizeRowViewHolder (View itemView) {
            super(itemView);
            tvPrizeName = (TextView) itemView.findViewById(R.id.tvPrizeName);
            tvLstResult = (TextView) itemView.findViewById(R.id.tvLstResult);
        }
    }
}
