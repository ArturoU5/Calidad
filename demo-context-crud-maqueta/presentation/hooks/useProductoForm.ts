import { useState } from 'react';
import { Alert } from 'react-native';
import { Producto } from '../../domain/entities/Producto';


export function useProductoForm() {
  const [nombre, setNombre] = useState('');
  const [precio, setPrecio] = useState('');
  const [editandoId, setEditandoId] = useState<string | null>(null);

  const limpiar = () => {
    setNombre('');
    setPrecio('');
    setEditandoId(null);
  };

  // TODO 6 - En clase, esta función debe llamar:

  const guardar = () => {
    const precioNumero = Number(precio);
    if (!nombre.trim() || !precio.trim() || Number.isNaN(precioNumero)) {
      Alert.alert('Datos incompletos', 'Ingresa un nombre y un precio válido.');
      return;
    }

    const datos = { nombre: nombre.trim(), precio: precioNumero };
    console.log('Datos listos para enviar al Context:', datos);

    Alert.alert(
      'Pendiente',
      editandoId
        ? 'Aquí conectaremos actualizar(id, datos) desde Context API.'
        : 'Aquí conectaremos crear(datos) desde Context API.',
    );

    limpiar();
  };

  const comenzarEdicion = (producto: Producto) => {
    setNombre(producto.nombre);
    setPrecio(producto.precio.toString());
    setEditandoId(producto.id);
  };

  return { nombre, setNombre, precio, setPrecio, editandoId, guardar, limpiar, comenzarEdicion };
}
