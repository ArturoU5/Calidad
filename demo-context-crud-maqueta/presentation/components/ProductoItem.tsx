import { Text, TouchableOpacity, View } from 'react-native';
import { Producto } from '../../domain/entities/Producto';

interface ProductoItemProps {
  producto: Producto;
  onEditar: () => void;
  onEliminar: () => void;
}

// PASO 15 - Componente visual para cada item de la lista.
export function ProductoItem({ producto, onEditar, onEliminar }: ProductoItemProps) {
  return (
    <View className="mb-3 flex-row items-center rounded-2xl bg-white p-4">
      <View className="flex-1">
        <Text className="text-lg font-extrabold text-slate-800">{producto.nombre}</Text>
        <Text className="mt-1 text-slate-500">S/ {producto.precio.toFixed(2)}</Text>
      </View>
      <TouchableOpacity className="mr-2 rounded-lg bg-amber-100 p-3" onPress={onEditar}>
        <Text>Editar</Text>
      </TouchableOpacity>
      <TouchableOpacity className="rounded-lg bg-rose-100 p-3" onPress={onEliminar}>
        <Text className="text-rose-700">Eliminar</Text>
      </TouchableOpacity>
    </View>
  );
}
