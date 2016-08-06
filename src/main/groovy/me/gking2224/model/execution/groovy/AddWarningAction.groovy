package me.gking2224.model.execution.groovy

class AddWarningAction extends Action {
    
    def code
    def summary
    def detail

    def AddWarningAction(def delegate, int code, String summary, String detail, def logger) {
        super(delegate, logger);
        this.code = code
        this.summary = summary
        this.detail = detail
    }
    
    def doIt() {
        logger.trace("AddWarningAction.doIt()")
        delegate.addWarning(code, summary, detail)
    }
    
    def stop() {
        delegate.addWarning(code, summary, detail)
        return NullAction.INSTANCE
    }

}
