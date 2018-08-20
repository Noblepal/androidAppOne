package icelabs.eeyan.bigman.volunteeapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class JobContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<JobItem> ITEMS = new ArrayList<JobItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, JobItem> ITEM_MAP = new HashMap<String, JobItem>();

    static {

        addItem(new JobItem("1", "Medical Sales Representative", "Gruber Pharmaceuticals", "Global Pharmacy", "Part time", "Kiambu", "2 mins ago",
                "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum " +
                        "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem " +
                        "Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum " +
                        "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem" +
                        " Ipsum Lorem Ipsum Lorem Ipsum", "3.5", "23", "14", R.drawable.landscape));

        addItem(new JobItem("2", "Sales Management", "Otto Enterprise", "Global Pharmacy", "Part time", "Kiambu", "2 mins ago",
                "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum " +
                        "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem " +
                        "Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum " +
                        "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem" +
                        " Ipsum Lorem Ipsum Lorem Ipsum", "2.5", "41", "9", R.drawable.landscape));

    }

    public static void addItem(JobItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.mID, item);
    }

    public static class JobItem {
        public String mID;
        private String mJobTitle;
        private String mCompanyName;
        private String mJobDescription;
        private String mJobType;
        private String mLocation;
        private String mPostTime;
        private String mJobDetails;
        private String mRating;
        private String mRatingUsers;
        private String mDuration;
        private int mImageResource;

        public JobItem() {
        }

        public JobItem(String mID, String mJobTitle, String mCompanyName, String mJobDescription, String mJobType, String mLocation, String mPostTime, String mJobDetails, String mRating, String mRatingUsers, String mDuration, int mImageResource) {
            this.mID = mID;
            this.mJobTitle = mJobTitle;
            this.mCompanyName = mCompanyName;
            this.mJobDescription = mJobDescription;
            this.mJobType = mJobType;
            this.mLocation = mLocation;
            this.mPostTime = mPostTime;
            this.mJobDetails = mJobDetails;
            this.mRating = mRating;
            this.mRatingUsers = mRatingUsers;
            this.mDuration = mDuration;
            this.mImageResource = mImageResource;
        }

        public String getmID() {
            return mID;
        }

        public void setmID(String mID) {
            this.mID = mID;
        }

        public String getmJobTitle() {
            return mJobTitle;
        }

        public void setmJobTitle(String mJobTitle) {
            this.mJobTitle = mJobTitle;
        }

        public String getmCompanyName() {
            return mCompanyName;
        }

        public void setmCompanyName(String mCompanyName) {
            this.mCompanyName = mCompanyName;
        }

        public String getmJobDescription() {
            return mJobDescription;
        }

        public void setmJobDescription(String mJobDescription) {
            this.mJobDescription = mJobDescription;
        }

        public String getmJobType() {
            return mJobType;
        }

        public void setmJobType(String mJobType) {
            this.mJobType = mJobType;
        }

        public String getmLocation() {
            return mLocation;
        }

        public void setmLocation(String mLocation) {
            this.mLocation = mLocation;
        }

        public String getmPostTime() {
            return mPostTime;
        }

        public void setmPostTime(String mPostTime) {
            this.mPostTime = mPostTime;
        }

        public String getmJobDetails() {
            return mJobDetails;
        }

        public void setmJobDetails(String mJobDetails) {
            this.mJobDetails = mJobDetails;
        }

        public String getmRating() {
            return mRating;
        }

        public void setmRating(String mRating) {
            this.mRating = mRating;
        }

        public String getmRatingUsers() {
            return mRatingUsers;
        }

        public void setmRatingUsers(String mRatingUsers) {
            this.mRatingUsers = mRatingUsers;
        }

        public String getmDuration() {
            return mDuration;
        }

        public void setmDuration(String mDuration) {
            this.mDuration = mDuration;
        }

        public int getmImageResource() {
            return mImageResource;
        }

        public void setmImageResource(int mImageResource) {
            this.mImageResource = mImageResource;
        }
    }
}
