function loggerMiddleware(req, res, next) {
    const now = new Date().toISOString();
    console.log(`[${now}] ${req.method} ${req.url}`);
    // Important : appeler next() pour passer au middleware / à la route suivante
    next();
  }
  
  module.exports = loggerMiddleware;