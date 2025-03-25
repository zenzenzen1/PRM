package com.example.tourbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tourbooking.Entity.Status;
import com.example.tourbooking.R;
import com.example.tourbooking.Entity.Order;
import com.example.tourbooking.Entity.Tour;
import com.example.tourbooking.admin.order.OrderDetail;
import com.example.tourbooking.helpler.FormatUtils;
import com.example.tourbooking.repository.TourRepository;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;
    private List<Tour> tourList;
    private ActivityResultLauncher<Intent> launcher;
    
    
    public AdminOrderAdapter(Context context, List<Order> orderList, ActivityResultLauncher<Intent> launcher) {
        this.context = context;
        this.orderList = orderList;
        this.launcher = launcher;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        TourRepository tourRepository = new TourRepository(context);
        Tour tour = tourRepository.getTour(order.getTourId());

        // Hiển thị dữ liệu trong ViewHolder
        holder.nameTour.setText("Tour: " + tour.getTile());
        holder.statusTour.setText(Status.StatusEnum.getStatusNameById(order.getStatusId()));
        holder.priceTour.setText("Price: " + FormatUtils.formatCurrency(order.getTotalFee()));
        Glide.with(context)
                .load(tour.getImage())
                .error(R.drawable.placeholder)
                .into(holder.imgTour);
        
        String departureDayString = FormatUtils.formatDate(order.getDepartureDay());
        String endDayString = FormatUtils.formatDate(order.getEndDate());
        String orderDay = FormatUtils.formatDate(order.getOrderDate());


        holder.btnOrderDetail.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderDetail.class);
            intent.putExtra("orderId", order.getId());
            intent.putExtra("tourId", order.getTourId());
            intent.putExtra("fee", order.getTotalFee());
            intent.putExtra("statusId", order.getStatusId());
            intent.putExtra("userId", order.getUserId());
            intent.putExtra("numPer", order.getNumberOfPerson());
            intent.putExtra("orderDay",orderDay);
            intent.putExtra("departDay", departureDayString);
            intent.putExtra("endDay", endDayString);
//            context.startActivity(intent);
            launcher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView nameTour, statusTour, priceTour;
        ImageView imgTour;
        Button btnOrderDetail;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTour = itemView.findViewById(R.id.admin_name_tour);
            statusTour = itemView.findViewById(R.id.admin_status_tour);
            priceTour = itemView.findViewById(R.id.admin_price_tour);
            imgTour = itemView.findViewById(R.id.admin_img_tour);
            btnOrderDetail = itemView.findViewById(R.id.btnAdminOrderDetail);
        }

    }

}
