import { dirname } from "path";
import { fileURLToPath } from "url";
import { FlatCompat } from "@eslint/eslintrc";
import typescriptParser from "@typescript-eslint/parser"

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const compat = new FlatCompat({
  baseDirectory: __dirname,
  recommended: true
});

const eslintConfig = [
  ...compat.extends("next/core-web-vitals", "next/typescript"),
  ...compat.extends("plugin:@typescript-eslint/recommended"),
  ...compat.extends("plugin:react/recommended"),
  ...compat.extends("plugin:prettier/recommended"),

  {
    files: ["**/*.ts", "**/*.tsx"],
    languageOptions: {
      parser: typescriptParser,
      parserOptions: {
        ecmaVersion: 2020,
        sourceType: "module",
        ecmaFeatures: { jsx: true }
      }
    },
    rules: {
      // TypeScript unsafe 관련 경고 해제
      "@typescript-eslint/no-unsafe-assignment": "off",
      "@typescript-eslint/no-unsafe-member-access": "off",
      "@typescript-eslint/no-unsafe-call": "off",

      // any 사용 허용
      "@typescript-eslint/no-explicit-any": "off",

      // Prettier
      "prettier/prettier": ["error", { endOfLine: "auto" }],

      // React 관련
      "react/react-in-jsx-scope": "off",

      // 함수형 컴포넌트 return type 권장 해제
      "@typescript-eslint/explicit-function-return-type": "off"
    },
    settings: {
      react: { version: "detect" }
    }
  }
];

export default eslintConfig;
