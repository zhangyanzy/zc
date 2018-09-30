package cn.zhaocaiapp.zc_app_android.views.commerclal;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.uitl.TConstant;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.request.commerclal.GetActivityStatus;
import cn.zhaocaiapp.zc_app_android.bean.request.commerclal.UploadRequest;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityUploadActivityBinding;
import cn.zhaocaiapp.zc_app_android.util.FileUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PhotoPickerUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtils;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jph.takephoto.uitl.TUriParse.parseOwnUri;

public class UploadActivityActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener
        , RadioGroup.OnCheckedChangeListener {

    private ActivityUploadActivityBinding binding;
    private NavigationTopBar mNavigationTopBar;

    private final static int VIDEO_KU = 101;
    private static String TAG = "UploadActivityActivity";
    private String videoPath;
    private TimePickerView mTime;
    private UploadRequest request;
    private BigDecimal rewardAmount;
    private PhotoHelper photoHelper;
    private String coverImage;//活动封面
    private String financeAuditStatus;// 审核状态 1待审核 3审核未通过
    private Long kid;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_activity);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.chooseActivityMoney.setOnCheckedChangeListener(this);
        photoHelper = PhotoHelper.of(binding.ll, false);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.upload_activity_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("上传活动");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        getActivityStatus();
    }

    @Override
    public void leftImageClick() {
        finish();
    }

    @Override
    public void rightImageClick() {

    }

    @Override
    public void alignRightLeftImageClick() {

    }

    /**
     * 启动相册
     */
    private void selectVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, UploadActivityActivity.VIDEO_KU);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case UploadActivityActivity.VIDEO_KU:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    uploadFile(getFileWithUri(uri, UploadActivityActivity.this));
                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(UploadActivityActivity.this, uri);
                    Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
                    if (bitmap != null) {
                        binding.videoImage.setImageBitmap(bitmap);
                        mediaMetadataRetriever.release();
                        if (binding.videoImage != null) {
                            binding.upDownVideo.setVisibility(View.GONE);
                        } else {
                            binding.upDownVideo.setVisibility(View.VISIBLE);
                        }
                    }
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static File getFileWithUri(Uri uri, Activity activity) {
        String path = null;
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String[] filePathColumn = {MediaStore.Video.Media.DATA};
            Cursor cursor = activity.getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (columnIndex >= 0) {
                path = cursor.getString(columnIndex);  //获取照片路径
            } else if (TextUtils.equals(uri.getAuthority(), TConstant.getFileProviderName(activity))) {
                path = parseOwnUri(activity, uri);
            }
            cursor.close();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
        }
        return TextUtils.isEmpty(path) ? null : new File(path);

    }

    private void uploadFile(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type:video/mp4"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("data", file.getName(), requestBody);
        String descriptionString = "This is a description";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        HttpUtil.post(Constants.URL.VIDEO_UPLOAD, part, description).subscribe(new BaseResponseObserver<Object>() {
            @Override
            public void success(Object s) {
                Log.i(TAG, "videoPath: " + s);
                videoPath = (String) s;
            }

            @Override
            public void error(Response<Object> response) {
                Log.i(TAG, "error: " + response);
            }
        });
    }

    private void createActivityInfo() {
        request = new UploadRequest();
        request.setActivityVideoOutUrl(videoPath);
        request.setRewardAmount(rewardAmount);
        request.setName(binding.activityTitle.getText().toString());
        request.setMaxUser(binding.maxUser.getText().toString());
        request.setContentRich(binding.activityDemand.getText().toString());
        BigDecimal bigDecimal = new BigDecimal(request.getMaxUser());
        if (request.getMaxUser() != null && request.getRewardAmount() != null) {
            request.setTotalAmount(bigDecimal.multiply(request.getRewardAmount()));
        }
        request.setActivityImageOutUrl(coverImage);
        //时间判断
        request.setStartTime(binding.startTime.getText().toString().trim());
        request.setEndTime(binding.endTime.getText().toString().trim());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date start = format.parse(request.getStartTime());
            Date end = format.parse(request.getEndTime());

            int compareTo = start.compareTo(end);
            if (compareTo == 1) {
                ToastUtils.showShortToast(UploadActivityActivity.this, "活动开始时间不能活动结束时间");
                return;
            } else if (compareTo == 0) {
                ToastUtils.showShortToast(UploadActivityActivity.this, "活动开始时间与活动结束时间不能相等");
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (kid != null) {
            request.setKid(kid);
        }
        if (request.getEndTime() == null && request.getStartTime() == null)
            ToastUtils.showShortToast(UploadActivityActivity.this, "请选择活动时间");
        else if (request.getMaxUser() == null && request.getRewardAmount() == null)
            ToastUtils.showShortToast(UploadActivityActivity.this, "活动人数与个人金额不得为空");
        else if (request.getActivityImageOutUrl() == null && request.getActivityVideoOutUrl() == null)
            ToastUtils.showShortToast(UploadActivityActivity.this, "活动视频与活动封面不能为空");
        else {
            upLoadActivity();
        }

    }

    private void upLoadActivity() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("kid", request.getKid());
        params.put("startTime", request.getStartTime());
        params.put("endTime", request.getEndTime());
        params.put("name", request.getName());
        params.put("totalAmount", request.getTotalAmount());
        params.put("rewardAmount", request.getRewardAmount());
        params.put("maxUser", request.getMaxUser());
        params.put("contentRich", request.getContentRich());
        params.put("activityImageOutUrl", request.getActivityImageOutUrl());
        params.put("activityVideoOutUrl", request.getActivityVideoOutUrl());

        HttpUtil.post(Constants.URL.ADD_ACTIVITY, params).subscribe(new BaseResponseObserver<String>() {
            @Override
            public void success(String s) {
                ToastUtils.showShortToast(UploadActivityActivity.this, s);
            }

            @Override
            public void error(Response<String> response) {
                ToastUtils.showShortToast(UploadActivityActivity.this, response.getDesc());
            }
        });

    }

    /**
     * 获取活动信息
     */
    private void getActivityStatus() {
        HttpUtil.get(Constants.URL.GET_ACTIVITY_STATUS).subscribe(new BaseResponseObserver<GetActivityStatus>() {
            @Override
            public void success(GetActivityStatus status) {
                if (status != null) {
                    if (status.getFinanceAuditStatus().equals("1")) {//待审核
                        financeAuditStatus = status.getFinanceAuditStatus();
                    } else {
                        Glide.with(getApplicationContext()).load(status.getActivityImageOutUrl()).into(binding.cover);
                        binding.activityTitle.setText(status.getName());
                        binding.startTime.setText(status.getStartTime());
                        binding.endTime.setText(status.getEndTime());
                        binding.maxUser.setText(status.getMaxUser());
                        if (status.getRewardAmount().equals(1)) {
                            binding.itemOne.setChecked(true);
                        } else if (status.getRewardAmount().equals(2)) {
                            binding.itemTwo.setChecked(true);
                        } else if (status.getRewardAmount().equals(5)) {
                            binding.itemFive.setChecked(true);
                        }
                        binding.activityDemand.setText(status.getContentRich());
                    }
                    if (status.getKid() != null) {
                        kid = status.getKid();
                    }
                }

            }

            @Override
            public void error(Response<GetActivityStatus> response) {

            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.item_one:
                rewardAmount = new BigDecimal(1);
                break;
            case R.id.item_two:
                rewardAmount = new BigDecimal(2);
                break;
            case R.id.item_five:
                rewardAmount = new BigDecimal(5);
                break;
            default:
                break;
        }
    }

    private void chooseTime(String title, TextView view) {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatYears = new SimpleDateFormat("yyyy");
        String yearsStr = formatYears.format(curDate);
        int yearsInt = (int) Double.parseDouble(yearsStr);

        SimpleDateFormat formatterMouth = new SimpleDateFormat("MM ");
        String mouthStr = formatterMouth.format(curDate);
        int mouthInt = (int) Double.parseDouble(mouthStr);

        SimpleDateFormat formatterDay = new SimpleDateFormat("dd ");
        String day_str = formatterDay.format(curDate);
        int dayInt = (int) Double.parseDouble(day_str);

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 11, 0);


        //时间选择器
        mTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                view.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, true})
                .setTitleText(title)
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false)
                .setRangDate(startDate, endDate)
                .build();
        mTime.show();
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void postPhoto(View view, Context context) {
        manageKeyBord(view, this);
        PhotoPickerUtil.init(context);
        PhotoPickerUtil.setContent("选择照片", new String[]{"拍照", "从相册选择"}, null);
        PhotoPickerUtil.show(photoListener);
    }

    private PhotoPickerUtil.OnItemClickListener photoListener = new PhotoPickerUtil.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            photoHelper.onClick(position, getTakePhoto());
        }
    };

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String image = result.getImage().getCompressPath();
        File file = new File(image);
        uploadCoverImage(file);
    }


    //上传照片到阿里云
    private void uploadCoverImage(File file) {
        Map<String, String> map = new HashMap<>();
        map.put("postfix", ".jpg");
        map.put("base64Str", FileUtil.fileToStream(file));
        HttpUtil.post(Constants.URL.UPLOAD_IMAGE, map).subscribe(new BaseResponseObserver<String>() {
            @Override
            public void success(String s) {
                if (GeneralUtils.isNotNullOrZeroLenght(s)) {
                    coverImage = s;
                    PictureLoadUtil.loadPicture(UploadActivityActivity.this, coverImage, binding.cover);
                    binding.upDownCover.setVisibility(View.GONE);
                } else {
                    ToastUtil.makeText(UploadActivityActivity.this, getString(R.string.cover_upload_failure));
                }
            }

            @Override
            public void error(Response<String> response) {

            }
        });
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.up_down_video_root:
                    if (financeAuditStatus != null) {
                        if (financeAuditStatus.equals("1")) {
                            binding.upDownVideoRoot.setClickable(false);
                            return;
                        } else {
                            binding.upDownVideoRoot.setClickable(true);
                        }
                    } else {
                        selectVideo();
                    }

                    break;
                case R.id.start_time:
                    if (financeAuditStatus != null) {
                        if (financeAuditStatus.equals("1")) {
                            binding.startTime.setClickable(false);
                            return;
                        } else {
                            binding.startTime.setClickable(true);
                        }
                    } else {
                        chooseTime("活动开始时间", binding.startTime);
                    }
                    break;
                case R.id.end_time:
                    if (financeAuditStatus != null) {
                        if (financeAuditStatus.equals("1")) {//活动待审核 不可被修改
                            binding.endTime.setClickable(false);
                            return;
                        } else {
                            binding.endTime.setClickable(true);

                        }
                    } else {
                        chooseTime("活动结束时间", binding.endTime);
                    }
                    break;
                case R.id.merchant_ca_submit:
                    if (financeAuditStatus != null) {
                        if (financeAuditStatus.equals("1")) {//活动待审核 不可被修改
                            binding.merchantCaSubmit.setClickable(false);
                            return;
                        } else {
                            binding.merchantCaSubmit.setClickable(true);
                        }
                    } else {
                        createActivityInfo();
                    }
                    break;
                case R.id.up_down_cover_root:
                    if (financeAuditStatus != null) {
                        if (financeAuditStatus.equals("1")) {//活动待审核 不可被修改
                            binding.upDownCoverRoot.setClickable(false);
                            return;
                        } else {
                            binding.upDownCoverRoot.setClickable(true);
                        }
                    }
                    postPhoto(binding.upDownVideoRoot, UploadActivityActivity.this);
                    break;
                default:
                    break;

            }
        }
    }
}
