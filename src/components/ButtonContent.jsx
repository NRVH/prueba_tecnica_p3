export const ButtonContent = (p) => {
    if(p.loading) {
        return <i className="fa fa-spinner fa-spin"></i>;
    }
    return <>{p.name}</>;
}