import { render, screen } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {

});
act(() => {
    mockAxios.post.mockImplementation(
        async () => Promise.resolve(availabilitySuccessMockData)
    );
});