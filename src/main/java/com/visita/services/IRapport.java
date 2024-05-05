package com.visita.services;

import java.util.List;

public interface IRapport<T>
{
    public void ajouter(T t);
    public void supprimer(T t);
    public void modifier(T t);
    public List<T> afficher();
}
