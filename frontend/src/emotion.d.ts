/// <reference types="@emotion/react/types/css-prop" />
import '@emotion/react';

declare module 'react' {
  interface HTMLAttributes<T> extends DOMAttributes<T> {
    css?: import('@emotion/react').CSSObject | import('@emotion/react').SerializedStyles;
  }
}