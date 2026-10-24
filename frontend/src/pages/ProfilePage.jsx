import { useState, useRef } from 'react';
import { api } from '../api/client';
import { useAuth } from '../context/useAuth';

export default function ProfilePage() {
  const { user, token, logout } = useAuth();
  const [editing, setEditing] = useState(false);
  const [name, setName] = useState(user?.name || '');
  const [email, setEmail] = useState(user?.email || '');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const [avatar, setAvatar] = useState(() => localStorage.getItem('studysync_avatar'));
  const fileRef = useRef(null);

  // Validaciones en el front antes de enviar
  function validate() {
    if (!name.trim()) return 'El nombre no puede estar vacío';
    if (!email.trim()) return 'El correo no puede estar vacío';
    if (!email.includes('@')) return 'El correo no tiene un formato válido';
    if (password && password.length < 6) return 'La contraseña debe tener al menos 6 caracteres';
    return null;
  }

  async function handleSave(e) {
    e.preventDefault();
    const validationError = validate();
    if (validationError) { setError(validationError); return; }
    setError('');
    setSuccess('');
    setLoading(true);
    try {
      const payload = { name: name.trim(), email: email.trim() };
      if (password) payload.password = password;
      await api.updateUser(user.id, payload);
      // Actualizar datos locales
      const updated = { ...user, name: payload.name, email: payload.email };
      localStorage.setItem('studysync_user', JSON.stringify(updated));
      setSuccess('Perfil actualizado correctamente');
      setEditing(false);
      setPassword('');
      // Forzar recarga del contexto — hack simple
      window.location.reload();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  function handleAvatarChange(e) {
    const file = e.target.files?.[0];
    if (!file) return;
    if (file.size > 2 * 1024 * 1024) { setError('La imagen no puede pesar más de 2MB'); return; }
    const reader = new FileReader();
    reader.onload = () => {
      const dataUrl = reader.result;
      setAvatar(dataUrl);
      localStorage.setItem('studysync_avatar', dataUrl);
    };
    reader.readAsDataURL(file);
  }

  function handleDeleteAccount() {
    if (!confirm('¿Estás completamente seguro? Esta acción NO se puede deshacer. Se eliminarán todas tus materias, planes y sesiones.')) return;
    if (!confirm('Última oportunidad. ¿Eliminar tu cuenta para siempre?')) return;
    api.deleteUser(user.id)
      .then(() => { logout(); window.location.href = '/login'; })
      .catch(err => setError(err.message));
  }

  const initials = (user?.name || 'U').split(' ').map(w => w[0]).join('').toUpperCase().slice(0, 2);

  if (!user) return null;

  return (
    <div className="max-w-2xl mx-auto">
      <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100 mb-6">Mi perfil</h1>

      {error && <div className="mb-4 p-3 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-400 text-sm rounded-lg">{error}</div>}
      {success && <div className="mb-4 p-3 bg-green-50 dark:bg-green-900/20 border border-green-200 dark:border-green-800 text-green-700 dark:text-green-400 text-sm rounded-lg">{success}</div>}

      {/* Avatar + info básica */}
      <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 mb-6">
        <div className="flex items-center gap-5">
          {/* Foto de perfil */}
          <div className="relative group">
            {avatar ? (
              <img src={avatar} alt="Avatar" className="w-20 h-20 rounded-full object-cover border-2 border-gray-200 dark:border-gray-600" />
            ) : (
              <div className="w-20 h-20 rounded-full bg-indigo-100 dark:bg-indigo-900/50 flex items-center justify-center text-2xl font-bold text-indigo-600 dark:text-indigo-400 border-2 border-gray-200 dark:border-gray-600">
                {initials}
              </div>
            )}
            <button
              onClick={() => fileRef.current?.click()}
              className="absolute inset-0 rounded-full bg-black/40 text-white text-xs font-medium opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center"
            >
              Cambiar
            </button>
            <input ref={fileRef} type="file" accept="image/*" onChange={handleAvatarChange} className="hidden" />
          </div>
          <div>
            <h2 className="text-xl font-semibold text-gray-900 dark:text-gray-100">{user.name}</h2>
            <p className="text-sm text-gray-500 dark:text-gray-400">{user.email}</p>
            <p className="text-xs text-gray-400 dark:text-gray-500 mt-1">ID: {user.id}</p>
          </div>
        </div>
      </div>

      {/* Formulario de edición */}
      <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6 mb-6">
        <div className="flex items-center justify-between mb-4">
          <h3 className="text-lg font-semibold text-gray-900 dark:text-gray-100">
            {editing ? 'Editar información' : 'Información personal'}
          </h3>
          {!editing && (
            <button onClick={() => { setEditing(true); setError(''); setSuccess(''); }} className="px-3 py-1.5 text-sm bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">
              Editar
            </button>
          )}
        </div>

        {editing ? (
          <form onSubmit={handleSave} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">Nombre</label>
              <input type="text" value={name} onChange={e => setName(e.target.value)}
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                placeholder="Tu nombre completo" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">Correo electrónico</label>
              <input type="email" value={email} onChange={e => setEmail(e.target.value)}
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                placeholder="tu@email.com" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Nueva contraseña <span className="text-gray-400 font-normal">(dejar vacío para no cambiarla)</span>
              </label>
              <input type="password" value={password} onChange={e => setPassword(e.target.value)} minLength={6}
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-sm bg-white dark:bg-gray-700 dark:text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                placeholder="Mínimo 6 caracteres" />
            </div>
            <div className="flex gap-3 pt-2">
              <button type="button" onClick={() => { setEditing(false); setError(''); setPassword(''); setName(user.name); setEmail(user.email); }}
                className="flex-1 py-2 border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 text-sm rounded-lg hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors">
                Cancelar
              </button>
              <button type="submit" disabled={loading}
                className="flex-1 py-2 bg-indigo-600 text-white text-sm font-medium rounded-lg hover:bg-indigo-700 disabled:opacity-50 transition-colors">
                {loading ? 'Guardando...' : 'Guardar cambios'}
              </button>
            </div>
          </form>
        ) : (
          <div className="space-y-3">
            <div className="flex justify-between py-2 border-b border-gray-100 dark:border-gray-700">
              <span className="text-sm text-gray-500 dark:text-gray-400">Nombre</span>
              <span className="text-sm font-medium text-gray-900 dark:text-gray-100">{user.name}</span>
            </div>
            <div className="flex justify-between py-2 border-b border-gray-100 dark:border-gray-700">
              <span className="text-sm text-gray-500 dark:text-gray-400">Correo</span>
              <span className="text-sm font-medium text-gray-900 dark:text-gray-100">{user.email}</span>
            </div>
            <div className="flex justify-between py-2">
              <span className="text-sm text-gray-500 dark:text-gray-400">Contraseña</span>
              <span className="text-sm font-medium text-gray-900 dark:text-gray-100">••••••••</span>
            </div>
          </div>
        )}
      </div>

      {/* Zona de peligro */}
      <div className="bg-white dark:bg-gray-800 rounded-xl border border-red-200 dark:border-red-900/50 p-6">
        <h3 className="text-lg font-semibold text-red-600 dark:text-red-400 mb-2">Zona de peligro</h3>
        <p className="text-sm text-gray-500 dark:text-gray-400 mb-4">
          Eliminar tu cuenta es permanente. Se borrarán todas tus materias, planes y sesiones de estudio.
        </p>
        <button onClick={handleDeleteAccount}
          className="px-4 py-2 bg-red-600 text-white text-sm font-medium rounded-lg hover:bg-red-700 transition-colors">
          Eliminar mi cuenta
        </button>
      </div>
    </div>
  );
}
