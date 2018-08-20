package icelabs.eeyan.bigman.volunteeapp;

import android.app.Activity;
import android.app.Dialog;
import android.media.Rating;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Float.parseFloat;
import static java.lang.Float.valueOf;
import static java.lang.Integer.parseInt;

/**
 * A fragment representing a single Job detail screen.
 * This fragment is either contained in a {@link JobListActivity}
 * in two-pane mode (on tablets) or a {@link JobDetailActivity}
 * on handsets.
 */
public class JobDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private JobContent.JobItem mItem;

    private BottomSheetDialog mBottomSheetDialog;

    RatingBar ratingBar;
    TextView ratingText, ratingUsers, longDescription, duration, companyName;
    Button btnRateActivity, rateBtn, cancelBtn;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = JobContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getmJobTitle());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.job_detail, container, false);

        View bottomSheet = rootView.findViewById(R.id.framelayout_bottom_sheet);

        if (mItem != null) {
            btnRateActivity = rootView.findViewById(R.id.rateBtn);
            btnRateActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final View bottomSheetLayout = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
                    (bottomSheetLayout.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();
                        }
                    });
                    (bottomSheetLayout.findViewById(R.id.btn_rate)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "Thank you for your feedback", Toast.LENGTH_SHORT).show();
                            mBottomSheetDialog.dismiss();
                        }
                    });

                    mBottomSheetDialog = new BottomSheetDialog(getContext());
                    mBottomSheetDialog.setContentView(bottomSheetLayout);
                    mBottomSheetDialog.show();

                    TextView companyTitle = bottomSheetLayout.findViewById(R.id.company_title);
                    companyTitle.setText(mItem.getmCompanyName());

                    RatingBar bar = bottomSheetLayout.findViewById(R.id.rating_bar_active);
                    bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                            TextView ratingText = bottomSheetLayout.findViewById(R.id.tv_ratingText);
                            ratingBar.setRating(v);
                            ratingText.setText(ratingBar.getRating() + "/" + ratingBar.getNumStars());
                        }
                    });
                }
            });

            ratingBar = rootView.findViewById(R.id.mRating);
            ratingText = rootView.findViewById(R.id.ratingText);
            ratingUsers = rootView.findViewById(R.id.rating_users);
            duration = rootView.findViewById(R.id.duration);
            companyName = rootView.findViewById(R.id.company_name);
            longDescription = rootView.findViewById(R.id.long_description);

            ratingBar.setRating(parseFloat(mItem.getmRating()));
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    ratingText.setText((int) v);
                }
            });
            ratingUsers.setText(mItem.getmRatingUsers());
            companyName.setText(mItem.getmCompanyName());
            longDescription.setText(mItem.getmJobDetails());
            duration.setText(mItem.getmDuration());

        }


        return rootView;
    }
}
