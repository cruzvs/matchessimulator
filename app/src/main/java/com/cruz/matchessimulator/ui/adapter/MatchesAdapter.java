package com.cruz.matchessimulator.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cruz.matchessimulator.databinding.MatchItemBinding;
import com.cruz.matchessimulator.domain.Match;
import com.cruz.matchessimulator.ui.DetailActivity;

import java.util.List;

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
        holder.binding.txtHome.setText(match.getHomeTeam().getName());
        if(match.getHomeTeam().getScore()!= null) {
            holder.binding.txtScoreHome.setText(String.valueOf(match.getHomeTeam().getScore()));
        }
        Glide.with(context).load(match.getAwayTeam().getImage()).circleCrop().into(holder.binding.imgAwayTeam);
        holder.binding.txtAwayTeam.setText(match.getAwayTeam().getName());
        if(match.getAwayTeam().getScore()!= null) {
            holder.binding.txtScoreAway.setText(String.valueOf(match.getAwayTeam().getScore()));
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.Extra.MATCH,match);
            context.startActivity(intent);
        });

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
