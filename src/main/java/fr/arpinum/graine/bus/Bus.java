package fr.arpinum.graine.bus;

import com.google.common.util.concurrent.ListenableFuture;

public interface Bus {

    <TReponse> ListenableFuture<ResultatCommande<TReponse>> poste(Commande<TReponse> commande);

}