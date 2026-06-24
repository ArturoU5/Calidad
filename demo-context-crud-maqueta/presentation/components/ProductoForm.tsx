import { Text, TextInput, TouchableOpacity, View } from 'react-native';

interface ProductoFormProps {
  nombre: string;
  precio: string;
  editando: boolean;
  onNombreChange: (valor: string) => void;
  onPrecioChange: (valor: string) => void;
  onGuardar: () => void;
  onCancelar: () => void;
}

// PASO 14 - Componente visual del formulario.

export function ProductoForm(props: ProductoFormProps) {
  return (
    <View className="rounded-2xl bg-white p-4 shadow">
      <TextInput
        className="mb-3 rounded-xl border border-slate-300 px-4 py-3"
        placeholder="Nombre del producto"
        value={props.nombre}
        onChangeText={props.onNombreChange}
      />
      <TextInput
        className="mb-3 rounded-xl border border-slate-300 px-4 py-3"
        placeholder="Precio"
        value={props.precio}
        onChangeText={props.onPrecioChange}
        keyboardType="numeric"
      />
      <TouchableOpacity className="items-center rounded-xl bg-indigo-600 p-4" onPress={props.onGuardar}>
        <Text className="font-extrabold text-white">
          {props.editando ? 'Actualizar producto' : 'Crear producto'}
        </Text>
      </TouchableOpacity>
      {props.editando && (
        <TouchableOpacity className="items-center p-3" onPress={props.onCancelar}>
          <Text className="font-bold text-slate-500">Cancelar edición</Text>
        </TouchableOpacity>
      )}
    </View>
  );
}
