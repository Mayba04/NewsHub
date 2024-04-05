import { MessageInstance } from 'antd/es/message/interface';

export const useNotification = (messageApi: MessageInstance) => {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const showMessage = (type: 'success' | 'error', content: string) => {
        messageApi.open({
            type,
            duration: 3,
            content,
        });
    };

    const handleSuccess = (content: string): void => {
        showMessage('success', content);
    };

    const handleError = (error: any): void => {
        if (typeof error === 'string') {
            showMessage('error', error);
        } else {
            const errorsObject = error?.errors;
            let errorList = '';

            for (const field in errorsObject) {
                const fieldErrors = errorsObject[field];
                errorList += fieldErrors.map((errorMessage: string) => `${errorMessage} `);
            }
            showMessage('error', errorList);
        }
    };

    return { handleSuccess, handleError };
};