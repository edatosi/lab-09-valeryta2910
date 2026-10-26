package listas;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Lista simplemente enlazada genérica.
 *
 * INSTRUCCIONES PARA EL ESTUDIANTE
 * --------------------------------
 * 1. Cada método marcado con "TODO" lanza UnsupportedOperationException.
 *    Reemplace esa línea por su implementación.
 * 2. NO modifique las pruebas (src/test). 
 * 3. Resuelva en orden (Etapa 1, 2, 3...): cada etapa usa las anteriores.
 * 4. Solo se permite usar nodos propios (clase Nodo). No use
 *    java.util.LinkedList ni ArrayList dentro de esta clase.
 */
public class ListaEnlazada<T> {

    /** Primer nodo de la lista (null cuando la lista está vacía). */
    private Nodo<T> cabeza;

    /** Cantidad de elementos. Debe mantenerse actualizada en cada operación. */
    private int cantidad;

    public ListaEnlazada() {
        // Una lista nueva empieza sin nodos y con cantidad 0.
        this.cabeza = null;
        this.cantidad = 0;
    }

    // =====================================================================
    // ETAPA 1. Estado de la lista
    // =====================================================================

    /** @return true si la lista no tiene elementos. */
    public boolean estaVacia() {
        // La lista está vacía cuando la cantidad es 0.
        return cantidad == 0;
    }

    /** @return número de elementos de la lista. */
    public int tamanio() {
        // Devuelve cuántos elementos tiene la lista, sin modificar el contador.
        return cantidad;
    }

    // =====================================================================
    // ETAPA 2. Agregar en los extremos
    // =====================================================================

    /** Inserta el dato como nuevo primer elemento. */
    public void agregarAlInicio(T dato) {
        // El nodo nuevo apunta al antiguo primero y pasa a ser la cabeza.
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.setSiguiente(this.cabeza);
        this.cabeza = nuevoNodo;
        this.cantidad++;
    }

    /** Inserta el dato como nuevo último elemento. */
    public void agregarAlFinal(T dato) {
        // Si la lista está vacía, es igual que agregar al inicio.
        if(estaVacia()){
            agregarAlInicio(dato);
        }else{
            Nodo<T> puntero = this.cabeza;
            // Avanza hasta el último nodo (el que no tiene siguiente).
            while (puntero.getSiguiente() != null){
                puntero = puntero.getSiguiente();
            }
            Nodo<T> nuevoNodo = new Nodo<>(dato);
            puntero.setSiguiente(nuevoNodo);
            this.cantidad++;
        }
    }

    // =====================================================================
    // ETAPA 3. Consultar elementos
    // =====================================================================

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerPrimero() {
        // No hay primer elemento si la lista está vacía.
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        // El primer elemento es el dato del nodo que está en la cabeza.
        return cabeza.getDato();
    }

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        Nodo<T> puntero = this.cabeza;
        // Avanza hasta el último nodo (el que no tiene siguiente).
        while (puntero.getSiguiente() != null) {
            puntero = puntero.getSiguiente();
        }
        return puntero.getDato();
    }

    /**
     * @param indice posición (0 = primer elemento)
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T obtener(int indice) {
        // El índice debe estar entre 0 y tamanio() - 1.
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        Nodo<T> puntero = this.cabeza;
        // Avanza tantos nodos como indique el índice.
        for (int i = 0; i < indice; i++) {
            puntero = puntero.getSiguiente();
        }
        return puntero.getDato();
    }

    // =====================================================================
    // ETAPA 4. Búsqueda
    // =====================================================================

    /** @return true si algún elemento es igual (equals) al dato. Admite null. */
    public boolean contiene(T dato) {
        // Si indiceDe lo encuentra, devuelve una posición distinta de -1.
        return indiceDe(dato) != -1;
    }

    /** @return posición de la primera aparición del dato, o -1 si no está. */
    public int indiceDe(T dato) {
        Nodo<T> puntero = this.cabeza;
        int posicion = 0;
        // Recorre la lista nodo por nodo, llevando la cuenta de la posición.
        while (puntero != null) {
            // Objects.equals compara bien incluso si el dato es null.
            if (Objects.equals(puntero.getDato(), dato)) {
                return posicion;
            }
            puntero = puntero.getSiguiente();
            posicion++;
        }
        // Llegó al final sin encontrarlo.
        return -1;
    }

    // =====================================================================
    // ETAPA 5. Insertar y reemplazar en cualquier posición
    // =====================================================================

    /**
     * Inserta el dato en la posición indicada, desplazando los siguientes.
     * Se permite indice == tamanio() (equivale a agregar al final).
     *
     * @throws IndexOutOfBoundsException si indice < 0 o indice > tamanio()
     */
    public void insertar(int indice, T dato) {
        // Se permite indice == cantidad (al final), pero no más allá.
        if (indice < 0 || indice > cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        // Insertar en la posición 0 es lo mismo que agregar al inicio.
        if (indice == 0) {
            agregarAlInicio(dato);
            return;
        }
        // Se detiene en el nodo anterior a la posición donde irá el dato nuevo.
        Nodo<T> anterior = this.cabeza;
        for (int i = 0; i < indice - 1; i++) {
            anterior = anterior.getSiguiente();
        }
        // Primero el nodo nuevo apunta al que sigue, y luego el anterior apunta al nuevo.
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.setSiguiente(anterior.getSiguiente());
        anterior.setSiguiente(nuevoNodo);
        this.cantidad++;
    }

    /**
     * Reemplaza el dato de la posición indicada.
     *
     * @return el dato que había antes
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T reemplazar(int indice, T dato) {
        // Aquí el índice debe apuntar a un nodo que ya existe.
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        Nodo<T> puntero = this.cabeza;
        // Avanza hasta el nodo de esa posición.
        for (int i = 0; i < indice; i++) {
            puntero = puntero.getSiguiente();
        }
        // Guarda el dato viejo, pone el nuevo y devuelve el viejo.
        T datoAnterior = puntero.getDato();
        puntero.setDato(dato);
        return datoAnterior;
    }

    // =====================================================================
    // ETAPA 6. Eliminar
    // =====================================================================

    /**
     * Elimina y retorna el primer elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarPrimero() {
        // No se puede eliminar de una lista vacía.
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        // Guarda el dato, mueve la cabeza al siguiente nodo y descuenta.
        T dato = this.cabeza.getDato();
        this.cabeza = this.cabeza.getSiguiente();
        this.cantidad--;
        return dato;
    }

    /**
     * Elimina y retorna el último elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        // Caso de un solo elemento: eliminar el último es eliminar el primero.
        if (this.cabeza.getSiguiente() == null) {
            return eliminarPrimero();
        }
        // Se detiene en el penúltimo nodo (el que apunta al último).
        Nodo<T> penultimo = this.cabeza;
        while (penultimo.getSiguiente().getSiguiente() != null) {
            penultimo = penultimo.getSiguiente();
        }
        T dato = penultimo.getSiguiente().getDato();
        // El penúltimo pasa a ser el último.
        penultimo.setSiguiente(null);
        this.cantidad--;
        return dato;
    }

    /**
     * Elimina y retorna el elemento de la posición indicada.
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T eliminar(int indice) {
        // El nodo debe existir, igual que en reemplazar.
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        // Eliminar la posición 0 es lo mismo que eliminar el primero.
        if (indice == 0) {
            return eliminarPrimero();
        }
        // Se detiene en el nodo anterior al que se va a eliminar.
        Nodo<T> anterior = this.cabeza;
        for (int i = 0; i < indice - 1; i++) {
            anterior = anterior.getSiguiente();
        }
        Nodo<T> aEliminar = anterior.getSiguiente();
        T dato = aEliminar.getDato();
        // El anterior se salta el nodo eliminado.
        anterior.setSiguiente(aEliminar.getSiguiente());
        this.cantidad--;
        return dato;
    }

    /**
     * Elimina la primera aparición del dato.
     * @return true si se eliminó algo, false si el dato no estaba
     */
    public boolean eliminarValor(T dato) {
        // indiceDe ya compara con Objects.equals y devuelve -1 si no está.
        int indice = indiceDe(dato);
        if (indice == -1) {
            return false;
        }
        eliminar(indice);
        return true;
    }

    /** Deja la lista vacía. */
    public void vaciar() {
        // Sin referencia a la cabeza, el resto de nodos queda sin uso.
        this.cabeza = null;
        this.cantidad = 0;
    }

    // =====================================================================
    // ETAPA 7. Representación en texto
    // =====================================================================

    /**
     * Formato: "[1, 2, 3]". Lista vacía: "[]".
     * Un elemento null se imprime como "null".
     */
    @Override
    public String toString() {
        // StringBuilder arma el texto sin crear un String nuevo en cada paso.
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> puntero = this.cabeza;
        while (puntero != null) {
            // String.valueOf imprime "null" si el dato es null.
            sb.append(String.valueOf(puntero.getDato()));
            // Pone la coma solo si todavía queda otro nodo.
            if (puntero.getSiguiente() != null) {
                sb.append(", ");
            }
            puntero = puntero.getSiguiente();
        }
        sb.append("]");
        return sb.toString();
    }

    // =====================================================================
    // ETAPA 8. Retos
    // =====================================================================

    /**
     * Invierte la lista EN SITIO: no cree nodos nuevos, solo cambie
     * las referencias 'siguiente' de los nodos existentes.
     */
    public void invertir() {
        // Tres referencias: anterior, actual y siguiente.
        Nodo<T> anterior = null;
        Nodo<T> actual = this.cabeza;
        while (actual != null) {
            // Guarda el siguiente antes de cambiar el enlace, o se pierde el resto.
            Nodo<T> siguiente = actual.getSiguiente();
            // Da vuelta el enlace: ahora apunta hacia atrás.
            actual.setSiguiente(anterior);
            // Avanza las dos referencias.
            anterior = actual;
            actual = siguiente;
        }
        // Al final, "anterior" es el último nodo original: la nueva cabeza.
        this.cabeza = anterior;
    }

    /**
     * Retorna el elemento del medio. Si el tamaño es par, el segundo de los
     * dos centrales (ej.: [1,2,3,4] -> 3). Pista: dos punteros, uno avanza
     * de a 1 y otro de a 2.
     *
     * @throws NoSuchElementException si la lista está vacía
     */
    public T obtenerMedio() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        Nodo<T> lento = this.cabeza;
        Nodo<T> rapido = this.cabeza;
        // El rápido avanza de a 2 y el lento de a 1.
        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();
            rapido = rapido.getSiguiente().getSiguiente();
        }
        // Cuando el rápido llega al final, el lento está en el medio.
        return lento.getDato();
    }

    /**
     * Elimina los elementos repetidos dejando solo la primera aparición
     * de cada uno, conservando el orden. Ej.: [1,2,1,3,2] -> [1,2,3]
     */
    public void eliminarDuplicados() {
        Nodo<T> actual = this.cabeza;
        while (actual != null) {
            // Revisa los nodos que vienen después de "actual".
            Nodo<T> anterior = actual;
            while (anterior.getSiguiente() != null) {
                Nodo<T> candidato = anterior.getSiguiente();
                if (Objects.equals(actual.getDato(), candidato.getDato())) {
                    // Es repetido: se salta el nodo y se descuenta.
                    anterior.setSiguiente(candidato.getSiguiente());
                    this.cantidad--;
                } else {
                    // No es repetido: se avanza.
                    anterior = candidato;
                }
            }
            actual = actual.getSiguiente();
        }
    }
}
