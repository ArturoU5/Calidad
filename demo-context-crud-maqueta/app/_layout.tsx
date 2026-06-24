import '../global.css';

import { Stack } from 'expo-router';

export default function RootLayout() {
  return (
    // TODO 1 - Implementar Context API en clase.
    <Stack screenOptions={{ headerShown: false }} />
  );
}
