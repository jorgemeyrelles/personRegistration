// Paleta de cores do projeto - Tema azul profissional e confiável

export const colors = {
  // Cores principais - Azul profissional
  primary: {
    main: '#1976d2',           // Azul principal - Material Design Blue 700
    light: '#42a5f5',          // Azul claro - Material Design Blue 400
    dark: '#0d47a1',           // Azul escuro - Material Design Blue 900
    contrastText: '#ffffff'    // Texto sobre cores primárias
  },

  // Cores secundárias - Azul acinzentado para sofisticação
  secondary: {
    main: '#455a64',           // Azul acinzentado - Material Design Blue Grey 700
    light: '#78909c',          // Azul acinzentado claro - Material Design Blue Grey 400
    dark: '#263238',           // Azul acinzentado escuro - Material Design Blue Grey 900
    contrastText: '#ffffff'    // Texto sobre cores secundárias
  },

  // Cores de estado - Para feedback e ações
  success: {
    main: '#4caf50',           // Verde - sucesso
    light: '#81c784',          // Verde claro
    dark: '#388e3c',           // Verde escuro
    contrastText: '#ffffff'
  },

  error: {
    main: '#f44336',           // Vermelho - erro
    light: '#e57373',          // Vermelho claro
    dark: '#d32f2f',           // Vermelho escuro
    contrastText: '#ffffff'
  },

  warning: {
    main: '#ff9800',           // Laranja - aviso
    light: '#ffb74d',          // Laranja claro
    dark: '#f57c00',           // Laranja escuro
    contrastText: '#ffffff'
  },

  info: {
    main: '#2196f3',           // Azul informativo
    light: '#64b5f6',          // Azul informativo claro
    dark: '#1976d2',           // Azul informativo escuro
    contrastText: '#ffffff'
  },

  // Cores neutras - Para backgrounds e textos
  grey: {
    50: '#fafafa',             // Cinza muito claro - backgrounds
    100: '#f5f5f5',            // Cinza claro - cards
    200: '#eeeeee',            // Cinza - dividers
    300: '#e0e0e0',            // Cinza médio - borders
    400: '#bdbdbd',            // Cinza - disabled
    500: '#9e9e9e',            // Cinza - texto secundário
    600: '#757575',            // Cinza escuro - texto
    700: '#616161',            // Cinza muito escuro
    800: '#424242',            // Quase preto
    900: '#212121'             // Preto suave
  },

  // Backgrounds especiais
  background: {
    default: '#fafafa',        // Background padrão da aplicação
    paper: '#ffffff',          // Background de cards e modais
    navbar: '#1976d2',         // Background da navbar
    footer: '#263238',         // Background do footer
    sidebar: '#455a64'         // Background de sidebars
  },

  // Textos
  text: {
    primary: '#212121',        // Texto principal
    secondary: '#757575',      // Texto secundário
    disabled: '#bdbdbd',       // Texto desabilitado
    onPrimary: '#ffffff',      // Texto sobre cores primárias
    onSecondary: '#ffffff',    // Texto sobre cores secundárias
    onBackground: '#212121',   // Texto sobre background
    onSurface: '#212121'       // Texto sobre superfícies
  },

  // Gradientes para elementos especiais
  gradients: {
    primary: 'linear-gradient(135deg, #1976d2 0%, #42a5f5 100%)',
    secondary: 'linear-gradient(135deg, #455a64 0%, #78909c 100%)',
    hero: 'linear-gradient(135deg, #0d47a1 0%, #1976d2 50%, #42a5f5 100%)',
    card: 'linear-gradient(145deg, #ffffff 0%, #f5f5f5 100%)'
  },

  // Sombras - para depth e hierarquia
  shadows: {
    light: '0 2px 4px rgba(25, 118, 210, 0.1)',
    medium: '0 4px 8px rgba(25, 118, 210, 0.15)',
    heavy: '0 8px 16px rgba(25, 118, 210, 0.2)',
    navbar: '0 2px 8px rgba(0, 0, 0, 0.1)',
    card: '0 2px 12px rgba(0, 0, 0, 0.08)'
  },

  // Cores de status específicas para o sistema
  status: {
    active: '#4caf50',         // Verde - ativo
    inactive: '#9e9e9e',       // Cinza - inativo
    pending: '#ff9800',        // Laranja - pendente
    approved: '#2196f3',       // Azul - aprovado
    rejected: '#f44336',       // Vermelho - rejeitado
    draft: '#795548'           // Marrom - rascunho
  },

  // Cores específicas para o sistema de registro de pessoas
  person: {
    verified: '#4caf50',       // Verde - pessoa verificada
    unverified: '#ff9800',     // Laranja - pessoa não verificada
    blocked: '#f44336',        // Vermelho - pessoa bloqueada
    vip: '#9c27b0'             // Roxo - pessoa VIP
  }
} as const;

// Exportar cores individuais para uso mais simples
export const {
  primary,
  secondary,
  success,
  error,
  warning,
  info,
  grey,
  background,
  text,
  gradients,
  shadows,
  status,
  person
} = colors;

// Função helper para obter cor com transparência
export const withOpacity = (color: string, opacity: number): string => {
  // Remove # se presente
  const hex = color.replace('#', '');
  
  // Converte hex para RGB
  const r = parseInt(hex.substring(0, 2), 16);
  const g = parseInt(hex.substring(2, 4), 16);
  const b = parseInt(hex.substring(4, 6), 16);
  
  return `rgba(${r}, ${g}, ${b}, ${opacity})`;
};

// Função helper para verificar se uma cor é clara ou escura
export const isLightColor = (color: string): boolean => {
  const hex = color.replace('#', '');
  const r = parseInt(hex.substring(0, 2), 16);
  const g = parseInt(hex.substring(2, 4), 16);
  const b = parseInt(hex.substring(4, 6), 16);
  
  // Fórmula para luminância
  const luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255;
  
  return luminance > 0.5;
};

export default colors;
