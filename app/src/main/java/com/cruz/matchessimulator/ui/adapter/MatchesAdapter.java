package com.cruz.matchessimulator.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cruz.matchessimulator.databinding.MatchItemBinding;
import com.cruz.matchessimulator.domain.Match;

import java.lang.invoke.MutableCallSite;
import java.util.List;

import kotlin.jvm.internal.markers.KMutableList;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private final List<Match> matches;

    public MatchesAdapter(List<Match> matches){
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MatchItemBinding binding = MatchItemBinding.inflate(layoutInflater, parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Match match = matches.get(position);

        // Adaptar os dados da partida(recuperada da API) para nosso layout
        Glide.with(context).load(match.getHomeTeam().getImage()).circleCrop().into(holder.binding.imgHome);
        Glide.with(context).load(match.getAwayTeam().getImage()).circleCrop().into(holder.binding.imgAwayTeam);
        holder.binding.txtHome.setText(match.getHomeTeam().getName());
        holder.binding.txtAwayTeam.setText(match.getAwayTeam().getName());
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final MatchItemBinding binding;

        public ViewHolder(MatchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
