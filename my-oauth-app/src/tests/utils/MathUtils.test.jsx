import { add, multiply } from '../../utils/mathUtils';

test('adds two numbers correctly', () => {
  expect(add(2, 3)).toBe(5);
});

test('multiplies two numbers correctly', () => {
  expect(multiply(4, 5)).toBe(20);
});