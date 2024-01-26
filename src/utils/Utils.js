import Sweet from "sweetalert2";

export const showAlert = (iconType, title, text) => {
    return new Promise((resolve, reject) => {
        Sweet.fire({
            icon: iconType,
            title: title,
            html: text,
        })
            .then(() => {
                resolve();
            })
            .catch((error) => {
                reject(error);
            });
    });
};